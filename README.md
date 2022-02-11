<img src="kinderlogo.png">

This is our final project in Android applications course, as part of our B.Sc. Computer Science studies that was taught by Dr. Eliav Menachi.
using MVVM architecture, remote server Firebase, Room local DB, Live data, Google design guideline.

# Main idea :circus_tent: :bulb: 
Our application main idea is to connect between kindergarten teachers to parents using social media where each user can sign in as a parent or as an educator.
Each user can post review or opinion that can be positive or negative about the educators in order to help other parents to get an helpful opinion to enter their
children to specific kindergartens by others reviews. The educator can also share their professional experiences with the children and show the parents the children's
daily routine in their kindergarten.

# Technologies and Logic :construction_worker:
- Firebase - using remote server API for user authentication, profile images and posts images using the Storage of Firebase, posts and profiles collections in the Firestore DB.
- Room - using local DB, using the cache with the SQLite for objects and images.
- MVVM architecture - modular code arrangement using model for the "backstage" connection to the remote DB (Firebase) and view model. using the view model to connect between the
  model to the view. using the view that include xml files of the design and fragments for each one, using view model to the model.
- Activities and fragments - fragments to each xml file in order to build functionality to parts in the view. activities: main activity, managing the posts and the nav graph for each fragment.
  next activity, the login activity maange the menu for the login and the sign up.
  the last activity, the intro activity, manage the login part and divide between the sign up and login by knowing which user is logged in at a moment and show when starting the
  applcation the intro.
- Navgraph and menu - using 2 navgraphs, one is for managing the fragments of the posts and profiles, and the other managing the login and sign up. using safeArgs- transferring
  arguments with actions between each fragments by using the navgraphs. using 2 menus, the first menu helps to navigate between fragments when user is logged in such as personal profile of the
  connected user, creating posts, home page etc. also, specific menu for the login and sign up which doesn't let user nevigate between fragments without connecting into their user
  or creating one.
 - Live data - using 2 kinds of live data (live data and mutablelivedata). live data,  an observable data holder class that respects the lifecycle of other app components, such as activities, fragments, or services. 
 This awareness ensures LiveData only updates app component observers that are in an active lifecycle state. The two types, are used in the view model, we used it to connect 
 between the view and the model. the mutable used for editable values and the live data for the fixed valus.
 - Progress bar - spinners are activated when a page is loaded, after editing post or user's profile, after creating a post, after signing in and each action that reqired spinners.
 also, when a user wants to update the home page where all the posts are showing after deleting or editing, he can refresh and the spinner will showed up untill the page loaded.
 - Picasso - Picasso is open source and one of the widely used image download libraries in Android. It. Picasso simplifies the process of loading images from external URLs and 
 displays them on your application. we used picasso library for the profile image and posts images. We used it to resize images and fit them to the frame etc.
 
 # Users stories :closed_book: :computer:
  - Jasmin wants to join the application, so she needs ot fill the sign in form which includes the fields: email, password, name, phone, address and choosing Educaor/Parent. also,
 Jasmin can upload a profile picture.
 - Rotem wants to login the application after he logged out. He enter his email and password and than has an access to see user's connected permissions.
 - Jessica the educator wants to post the children's routine in her kindergarten and Rachel the parent wants to post about Jessica's proffesional behavior, 
 and uplaoding images, when each one can be the only one who can edit and delete the post after posting it (each person to his own posts).
 - Rachel wants to edit her profile. She click in the main menu on the profile item and enter the profile editing page there she can edit anything but her email and password.
 - Erika wants to see Jasmin and Jessica's posts. She click on the home item in the main menu and it will show her all the posts inculding her own posts.
 - Jonathan wants to sign out from the application so he can tap on the log out item in the main menu.
 
 # Demo :movie_camera:
 https://www.youtube.com/watch?v=NWliAOg1Ka8
 
 recommended to watch in HD.
 
 <img src="hr.png" width=100px, height=100px >
 
