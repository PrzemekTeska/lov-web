# Labor Omnia Vincit Web
The Web version of LOV with Spring Boot, Spring Security, Hibernate, Thymeleaf.

## [Try to use our App on heroku!](https://lovappweb.herokuapp.com/)


### LOV app is a time management tool for everyone to use on every Goal you want to achieve. 
We've created LOV mainly with the purpose of using it to organize ourselves to maintain a stable learning curve for our studies. 
That's why the first version of LOV was developed for the Android environment. We wanted our App to be quick to reach, so we could waste less time on self-organization. 

We quickly noticed that our App should not be only used for education but for every other goal we want to achieve. So, we restructured the Mobile App with the goal to make it more universal.

So, we've created a universal Android App for everyone to use. 

The development of LOV was so entertaining and challenging that we decided to create a new version of LOV based on the same concept, but using a completely different technology, with more care to details and new, better functionalities. 

## Structure

### 1. Goal:
      A Goal should be your long-term target. E.g.: Getting in shape for the summer season in two months. 
      So, You can create a goal by adding a Goalname, on your own or choosing one from already created names by our team. (We implemented that into every part of the App)
      The next step is of course setting the timeline you want your goal to be achieved in. We made it simple for you.
      Just choose start- and end date for your Goal from the Date selections.
      All your created goals are visible in a List in the "Goal" overlap.
      
### 2. Activities:
      Activities are your small steps to achieve the long-term Goals. E.g.: Run 2 kilometers or read a different book every week.
      Our Application is all about simplicity and time saving, so creating every little part of it is as simple and as fast as possible.
      Just choose a name, amount, unit, frequency, priority as points and connect it to one of your goals and you are good to go.
      We have created a half - automatic system that checks whether you have achieved your Activities in time or not! 
      If you did achieve the activities simply click the "Completed" button. Now you can see the progress column has changed. 
      The progress has been set based on the goal and frequency you have chosen. There is also a "Failed" button, that you might use if you want, but don't have to, because the App automatically checks if you have failed your daily/weekly tasks.
      
### 3. Rewards and Penalties:
      So now after you have set Goal and Activities you are able to start working on your goals. *Yea right theoretically you are able to...*
      We know everyone needs a little bit of help with their motivation. That's why we have implemented the Rewards and Penalties systems into our App.
      Just create them by giving a name, setting a minimum percentage of successfully completed activities (Reward) or a maximum amount of failed in a row activities (Penalty) and connecting it to an existing goal.
      Our app will track your Reward / Penalties progress, based on the your progress of all activities connected to the Rewards / Penalties goal.
      When you get the percentage/ failed in a row activities, the Reward / Penalty will automatically show up in a different table so you know your Reward / Penalty is active.
      
### 4. Ranking:
       We want to motivate you even more!
       That's why we also implemented a global ranking system that shows the top 10 users with the most amount of Points gathered from completing activities.
       Of course your current place in the list is also visible. 
       
### 5. Notifications:
       You can also set email notifications that we will send to you weekly / once every two weeks / once a month. Those notifications will tell you about your progress. 
       
       
## Security!
Of course we care about your security and that's why we implemented login authentication. You will also have to verify your email to be able to access the App.
We also care about your passwords by encrypting them with the BCrypt hashing function.
