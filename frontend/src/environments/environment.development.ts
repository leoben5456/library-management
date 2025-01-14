export const environment = {
    production: false,

    //authentification endpoint
    authApi: 'http://localhost:8080/auth-service/auth/login',

    // user registration endpoint
    userRegistrationApi: 'http://localhost:8080/user-service/user/inscription',

    // Get Books Endpoint
    BooksApi:'http://localhost:8080/livre-service/livres',

    //Create new Book Endpoint
    CreateBookApi:'http://localhost:8080/livre-service/livre/inscription',

    //Detete Book Endpoint
    DeleteBookApi:'http://localhost:8080/livre-service/livre/delete/',


    //Get All Books Reservations Endpoint
    AllBooksReservationsApi:'http://localhost:8080/reservation-service/reservations',
    
};
