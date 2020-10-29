//=== Filter pharmacy on order page
function filterPahrmacyAdress() {
    var filter_input = $(".service-filter-pahrmacy");
    var store_group = $(".service-store-group");
    var store = $(".service-store-item");
    var empty_pharmacy_block = $(".service-empty-pharmacy");

    var filter_array = new Array();

    filter_input.filter(":input:checked").each(function () {
        filter_array.push($(this).attr('name'));
    });
    
    var show_empty_pharmacy_block = true;

    //=== Store 
    store.each(function () {
        var _this = $(this);

        var this_key_filter = _this.data('filter_pharmacy').split(" ");

        if (filter_array.filter(function (i) {
            return this_key_filter.indexOf(i) < 0;
        }).length === 0) {
            _this.show();
            show_empty_pharmacy_block = false;
        } else {
            _this.hide();
        }
    });

    //=== Group of store 
    store_group.show();
    store_group.each(function () {
        var _this = $(this);

        if (_this.find(".service-store-item:visible").length) {
            _this.show();
        } else {
            _this.hide();
        }
    });

    //=== Markers on map
    if (typeof groupsAddress !== 'undefined') {
        $.each(groupsAddress, function (group_index, group) {
            $.each(group.items, function (index, _this) {                
                if ((typeof _this.filter_pharmacy !== 'undefined')) {
                    var this_key_filter = _this.filter_pharmacy.split(" ");

                    if (filter_array.filter(function (i) {
                        return this_key_filter.indexOf(i) < 0;
                    }).length === 0) {
                        groupsAddress[group_index].items[index].visible = 1;
                        show_empty_pharmacy_block = false;
                    } else {
                        groupsAddress[group_index].items[index].visible = 0;
                    }
                }
            });
        });
    }

    //=== Hide/show empty block text
    if (show_empty_pharmacy_block) {
        $('#address_map_ul .thisInn').append("<div class='menu'><div class='basket-page-place-map-elem'><div class='basket-page-place-map-elem-h'>Аптек не найдено</div></div></div>");
    }
}

function initAddress() {
    $(".map_loading").removeClass("map_loading");
    
    //=== Создаем карту
        if (typeof myMap === 'undefined') {
            var map_center = [56, 37];
            if ((typeof city_geo !== 'undefined') && (city_geo.modifier.center != "") && (city_geo.modifier.center != null)) {
                map_center = city_geo.modifier.center;
            }
            
            myMap = new ymaps.Map("address_map", {
                center: map_center,
                zoom: 8,
                controls: []
            }); 
            
        } else {
            $("#NewAddressTarget").empty();
            myMap.geoObjects.removeAll();
        }
    
    //=== Фильтруем аптеки
        filterPahrmacyAdress();
        
        clusterIconLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="font-family:SF UI Display;font-weight:600;font-size:16px;line-height:28px;color:#ffffff;">{{properties.geoObjects.length}}</div>'
        );

        collection = new ymaps.Clusterer({
            clusterIcons: clusterIcons,
            clusterIconContentLayout: clusterIconLayout
        });
        
        menu = $('<div class="menu"/>');

    if (typeof groupsAddress !== 'undefined') {
        for (var i = 0, l = groupsAddress.length; i < l; i++) {
            var create_group = false;
            if ((typeof groupsAddress[i].items !== 'undefined')) {
                for (var j = 0, m = groupsAddress[i].items.length; j < m; j++) {
                    if (groupsAddress[i].items[j].visible == 1) {
                        create_group = true;
                        break;
                    }
                }
            }
            
            if (create_group) {
                createMenuGroup(groupsAddress[i]);
            }
        }
    }

    function createMenuGroup (group) {
        // Пункт меню.
        //var menuItem = $('<div class="basket-page-place-map-elem"><div class="basket-page-place-map-elem-h">' + group.name + '</div></div>');
        var menuItem = $(group.menu_html);
        // Контейнер для подменю.
        var submenu = $('<div class="thisSubMenu" />');

        // Добавляем коллекцию на карту.
        myMap.geoObjects.add(collection);
        // Добавляем подменю.
        menuItem
            .append(submenu)
            // Добавляем пункт в меню.
            .appendTo(menu);
        for (var j = 0, m = group.items.length; j < m; j++) {
            createSubMenu(group.items[j], collection, submenu);
        }
    }

    function createSubMenu (item, collection, submenu) {
        // Разные опции для метки
        var activeOptions = placemark_map.activeOptions;
        var defaultOptions = placemark_map.defaultOptions;
        
        // Пункт подменю.
        //var submenuItem = $('<div class="basket-page-place-map-item" tabindex="0" id="ElemId' + item.id + '"> <div class="basket-page-place-map-item-inn"> <div class="basket-page-place-map-item-card"> <div class="basket-page-place-map-item-card-inn"> <div class="basket-page-place-map-item-top"> <div class="thisPlace">' + item.name + '</div><div class="thisPrice">2 379,<i>75 ₽</i></div></div><div class="basket-page-place-map-item-other"> <div class="thisOther">Аптека Эдельвейс</div><div class="thisOther">Аптека №15</div></div><div class="basket-page-place-map-item-link"><a class="thisLink ' + item.count_class + '">' + item.count + '</a></div><div class="basket-page-place-map-item-btn"><a href="/basket_pre_end.html" class="button button-buy">Выбрать</a></div></div></div><div class="basket-page-place-map-item-dropdown"> <div class="thisDD"> <div class="thisBasketInnElem"> <div class="thisBasketInnElem-img"> <div class="thisBasketInnElem-img-tc"><img src="images/pic/item1.png"></div></div><div class="thisBasketInnElem-info"> <div class="thisBasketInnElem-info-h">ТераФлю Экстра порошок для приготовления раствора лимон №10</div><div class="thisBasketInnElem-info-bottom"> <div class="thisBasketInnElem-info-bottom-t"> <div class="thisBasketInnElem-info-price">2 х 435,<i>00 ₽</i></div><div class="thisBasketInnElem-info-summ">870,<i>00 ₽</i></div></div></div></div></div><div class="thisBasketInnElem"> <div class="thisBasketInnElem-img"> <div class="thisBasketInnElem-img-tc"><img src="images/pic/item2.png"></div></div><div class="thisBasketInnElem-info"> <div class="thisBasketInnElem-info-h">ТераФлю Экстра порошок для приготовления раствора лимон №10</div><div class="thisBasketInnElem-info-bottom"> <div class="thisBasketInnElem-info-bottom-t"> <div class="thisBasketInnElem-info-price">1 х 135 211,50 ₽</div><div class="thisBasketInnElem-info-summ">135 211,<i>50 ₽</i></div></div></div></div></div><div class="thisBasketInnElem"> <div class="thisBasketInnElem-img"> <div class="thisBasketInnElem-img-tc"><img src="images/pic/item3.png"></div></div><div class="thisBasketInnElem-info"> <div class="thisBasketInnElem-info-h">ТераФлю Экстра порошок для приготовления раствора лимон №10</div><div class="thisBasketInnElem-info-none">нет в наличии</div></div></div></div></div></div></div>');
        var submenuItem = $(item.submenu_html);
        
        // Создаем метку.
        if ((typeof item.center[0] !== 'undefined') && (item.center[0] > 0) && (item.center[0] != null)) {
            var placemark = new ymaps.Placemark(item.center, {defaultOptions: defaultOptions, activeOptions: activeOptions, ElemId: item.id, hintContent: item.name, filter_pharmacy: item.filter_pharmacy}, defaultOptions);

            // Добавляем метку в коллекцию.
            collection.add(placemark);
            // Навешиваем один обработчик на коллекцию (event delegation)
            collection.events.add('click', function (e) {
                // placemark на который кликнули
                var activeGeoObject = e.get('target');
                var GeoObjectElemId = '#ElemId' + activeGeoObject.properties.get('ElemId');
                // Выставляем ему activeOptions
                activeGeoObject.options.set(activeGeoObject.properties.get('activeOptions'));
                // остальным выставляем defaultOptions
                collection.getGeoObjects().forEach(function callback(currentValue, index, array) {
                    if(currentValue !== activeGeoObject) {
                        currentValue.options.set(currentValue.properties.get('defaultOptions'));
                    }
                });

                // Показываем карточку в режиме карты в мобильнмо виде
                $('.basket-page-place-map-item').removeClass('active-xs');
                $(GeoObjectElemId).addClass('active-xs');
                // Фокус на карточку в списке
                var topPos = document.querySelector(GeoObjectElemId).offsetTop;
                document.querySelector('#NewTargetAddress').scrollTo({top: (topPos - 66), behavior: 'smooth'});
                $('.basket-page-place-map-item').removeClass('active-md');
                $(GeoObjectElemId).addClass('active-md');
            });
        }
        
        // Добавляем пункт в подменю.
        submenuItem
            .appendTo(submenu)
            // При клике по пункту подменю открываем/закрываем баллун у метки.
            .find('.basket-page-place-map-item-card')
            .bind('click', function (e) {
                if ((typeof item.center[0] !== 'undefined') && (item.center[0] > "") && (item.center[0] != null)) {
                    $("#openMapTab").click();
                    
                    $('.basket-page-place-map-item').removeClass('active-md').removeClass('active-xs');
                    $(e.currentTarget).closest('.basket-page-place-map-item').addClass('active-md').addClass('active-xs');
                    if (getState(placemark).isShown) {
                        centerBalloon(placemark);
                    } else {
                        myMap.setBounds(myMap.geoObjects.getBounds()).then(centerBalloon(placemark));
                    }
                }
                //return false;
                
            });
    }

    // Выбираем метку
    function centerBalloon (point) { 
        var state = getState(point),
            cluster = state.isClustered && state.cluster;

        /*if(cluster) {
            cluster.events.fire('click').then(function() {
                myMap.setCenter( point.geometry.getCoordinates(), 16 );

                point.options.set(point.properties.get('activeOptions'));
                collection.getGeoObjects().forEach(function callback(currentValue) {
                    if(currentValue !== point) {
                        currentValue.options.set(currentValue.properties.get('defaultOptions'));
                    }
                });
            });
        }
        else {*/
            myMap.setCenter( point.geometry.getCoordinates(), 18 );

            point.options.set(point.properties.get('activeOptions'));
            collection.getGeoObjects().forEach(function callback(currentValue) {
                if(currentValue !== point) {
                    currentValue.options.set(currentValue.properties.get('defaultOptions'));
                }
            });
        //}
    }

    function getState (point) {
        return collection.getObjectState(point);
    }

    // Добавляем меню в тэг BODY.
    menu.appendTo($('#address_map_ul .thisInn'));
    // Выставляем масштаб карты чтобы были видны все группы.
    myMap.setBounds(myMap.geoObjects.getBounds(), {
        checkZoomRange: true
    });

    $(window).resize(function() {
        myMap.container.fitToViewport();
    });
}

$(document).ready(function () {
    if ($('#address_map').length) {
        InitMapAddress();
        $('#openMapAddressTab').on('click', function () {
            $(".basket-page-place-map-item").removeClass("active-xs").removeClass("active-md");
            setTimeout(function(){
                InitMapAddress();
            }, 500);
        });
        
        //=== Check filter input
        if ($(".service-filter-pahrmacy").length) {
            $(".service-filter-pahrmacy").on('change', function () {                
                InitMapAddress();
                return false;
            });
        }

        function ClearHtml(callback) {
            $('#address_map').html('');
            $('#address_map_ul .thisInn').html('');
            callback();
        }
        function InitMapAddress() {
            ymaps.ready(initAddress);
        }
    }
});
