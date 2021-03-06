package ru.hse.guidehelper.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String BASE_URL = "http://192.168.0.161:8080";

    private final TourService tourService;
    private final UserService userService;
    private final ChatService chatService;
    private final FavoriteTourService favoriteTourService;
    private final OrderService orderService;

    private static Api instance;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        tourService = retrofit.create(TourService.class);
        userService = retrofit.create(UserService.class);
        chatService = retrofit.create(ChatService.class);
        favoriteTourService = retrofit.create(FavoriteTourService.class);
        orderService = retrofit.create(OrderService.class);
    }

    public TourService getTourService() {
        return tourService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public FavoriteTourService getFavoriteTourService() {
        return favoriteTourService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
