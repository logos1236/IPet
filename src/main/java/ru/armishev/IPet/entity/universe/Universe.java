package ru.armishev.IPet.entity.universe;

import java.time.Instant;
import java.util.List;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import ru.armishev.IPet.entity.event.EventFactory;
import ru.armishev.IPet.entity.event.IEvent;
import ru.armishev.IPet.entity.pet.IPet;

/*
    Вселенная, которая создает для питомца генерируемые события
*/
@Service
public class Universe implements IUniverse {
    @Autowired
    private EventFactory eventFactory;

    /*
    Список питомцев, которые уже в сесии и обрабатываются
     */
    private static WeakHashMap<IPet, String> active_pet_list = new WeakHashMap<>();

    /*
    Эмулируем временной отрезок жизни питомца
    */
    @Override
    public void timeMachine(IPet pet) {
        long current_time = Instant.now().getEpochSecond();
        if (checkStartTimeMachine(pet)) {
            if (isGenerateDataByEvent(pet)) {
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
    private boolean isGenerateDataByEvent(IPet pet) {
        boolean result = false;

        if (!active_pet_list.containsKey(pet)) {
            result = true;
        } else {
            String user_session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
            String owner_session_id = active_pet_list.get(pet);

            if (user_session_id.equals(owner_session_id)) {
                result = true;
            }
        }

        return result;
    }

    private void setPetToActiveList(IPet pet) {
        String user_session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
        active_pet_list.put(pet, user_session_id);
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
