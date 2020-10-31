package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import ru.armishev.IPet.entity.event.EventFactory;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.pet.IPet;
import ru.armishev.IPet.entity.pet.Pet;

/*
    Вселенная, которая создает для питомца генерируемые события
*/
@Service
public class Universe implements IUniverse {
    @Autowired
    private EventFactory eventFactory;

    @Autowired
    IPet pet;

    /*
    Список питомцев, которые уже в сесии и обрабатываются
     */
    private static WeakHashMap<IPet, ActivePet> active_pet_list = new WeakHashMap<>();

    private class ActivePet {
        IPet pet;
        String session_id;

        public ActivePet(IPet pet, String session_id) {
            this.pet = pet;
            this.session_id = session_id;
        }
    }
    /*
    Эмулируем временной отрезок жизни питомца
    */
    @Override
    public void timeMachine() {
        long current_time = Instant.now().getEpochSecond();
        if (checkStartTimeMachine(pet)) {
            if (isGenerateDataByEvent(pet)) {
                pet.loadFromDAO(pet.getId());
                setPetToActiveList(pet);

                for (long i_time = pet.getLastVisitTime(); i_time <= current_time; i_time++) {
                    createEventInPetLive(i_time, pet);
                }

                pet.saveToDAO();
            } else {
                pet.loadFromDAO(pet.getId());
            }
        }
    }

    /*
    Проверяем, есть ли смысл запускать генерацию событий
    */
    private boolean checkStartTimeMachine(IPet pet) {
        boolean result = true;
        long current_time = Instant.now().getEpochSecond();

        try {
            long critical_downtime = 300;
            if (current_time - pet.getLastVisitTime() > critical_downtime) {
                pet.escape();
                throw new Exception("Слишком долго отсутствовал хозяин");
            }

            if (!(pet.getLastVisitTime() > 0 && pet.getId() > 0)) {
                throw new Exception("Питомец еще не прописан в базе");
            }

            if (pet.isEscaped()) {
                throw new Exception("Питомец сбежал");
            }

        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /*
    Проверяем, обрабатывается ли сейчас питомец каким-то пользователем
    и является ли текущий пользователь инициатором обработки
    true - запускаем генерацию событий
    false - берем данные из базы
    */
    public boolean isGenerateDataByEvent(IPet pet) {
        boolean result = false;

        if (!active_pet_list.containsKey(pet)) {
            result = true;
        } else {
            String user_session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
            String owner_session_id = active_pet_list.get(pet).session_id;

            if (user_session_id.equals(owner_session_id)) {
                result = true;
            }
        }

        return result;
    }

    /*
    Добавляем питомца в список активных
     */
    private void setPetToActiveList(IPet pet) {
        if (!active_pet_list.containsKey(pet)) {
            String user_session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
            active_pet_list.put(pet, new ActivePet(pet, user_session_id));
        }
    }

    private IPet getPetFromActiveList(IPet pet) throws Exception {
        if (active_pet_list.containsKey(pet)) {
            return active_pet_list.get(pet).pet;
        }

        throw new Exception("Нет питомца в списке активных");
    }

    /*
    Создаем события в жизни питомца
     */
    private void createEventInPetLive(long current_time, IPet pet) {
        List<IEvent> eventList = eventFactory.getEventList();

        if (!eventList.isEmpty()) {
            for(IEvent event: eventList) {
                if (!pet.isEscaped()) {
                    event.execute(current_time);
                }
            }
        }
    }
}
