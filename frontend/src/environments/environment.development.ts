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

    //Check Book availability Endpoint
    CheckBookAvailabilityApi:'http://localhost:8080/reservation-service/reservation/check-livre-availability/',

    //Add Book to the wishlist Endpoint
    AddBookToWishlistApi:'http://localhost:8080/livre-service/livre/add/to-wishlist/',

    // Get user books in the wishlist Endpoint
    UserWishlistApi:'http://localhost:8080/livre-service/user/wishlist',


    //Reserve Book Endpoint
    ReserveBookApi:'http://localhost:8080/reservation-service/reservation/creation/',

  
   //Get books by genre Endpoint
    BooksByGenreApi:'http://localhost:8080/livre-service/livres/',


    //Change reservation status Endpoint
    ChangeReservationStatusApi:'http://localhost:8080/reservation-service/reservation/update/returned/',

    
    //Get user reservations Endpoint
    UserReservationsApi:'http://localhost:8080/reservation-service/reservation/user',


     //Gemini API
    geminiApiKey: 'AIzaSyCIDRaYO6tMK1iR6VcmCuYbTm3z9GiZTWk',
    geminiApiUrl: 'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent'
};


