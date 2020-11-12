# SPRING SECURITY IMPLEMENTATION USING JWT

### Project Structure

![projectStructure](https://user-images.githubusercontent.com/42442228/98954682-0ce02a80-2524-11eb-90dc-0182e0cf2027.png)

### Brief introduction about the Packages and files in the Project:

1.)com.example.jsonwebtoken.controller: This package contains the two controller classes used in the Project.


			i.) AuthenticationController: Controller used to sign in and sign out of the system.
			ii.) TestController: Controller used to test the APIs for the authentication.

2.)com.examples.jsonwebtokens.models: This package contains the models/entities.
	
			i.)Role: Entity class which stores all the valid roles of the application
			ii.)User: Entity class which stores the users registered in the application

3.)com.examples.jsonwebtokens.payload.request: This package can also be called as the DTO for the signin and signup functionality
	
			i.)LoginReuqest: DTO used for the Login of the users
			ii.)SignUpRequest: DTO used for the Sign up of the users

4.)com.examples.jsonwebtokens.payload.response: This package contains the response to various request by the user.
	
			i.)JwtResponse: POJO to store the JWT token which will be given to the user upon the request of the login
			ii.)MessageResponse: POJO to store the message response for any request

5.)com.examples.jsonwebtokens.repository: Package which contains various repostiry to access the DB
	
			i.)RoleRepository: Repository class to access the Role table
			ii.)UserRepository:Repository class to access the User table.


6.)com.examples.jsonwebtokens.security: This package contains the Security config file which configures the security for our application. This can be seen as the control panel of the security
			
			i.)WebSecurityConfig.java: File containing the security configuration of our application

			ii.)com.examples.jsonwebtokens.security.jwt: Sub package of security containing the JWT related files such as Class which will be used for the Exception handling in case anything goes wrong during the authentication process.
			
			iii.)com.examples.jsonwebtokens.security.services: Sub package containing files related to implementation of UserDetails and UserDetailsService to the spring security for authentication
	
	
### Tables used in the application

User Table: Used to store the User related information
					
					----------------------------------------
					| id | username | email       | password |
					|----|----------|-------------|----------|
					|1   |tushar    |abc@gmail.com|abcd      |
					|2   |kumar     |def@gmail.com|efgh      |
					 ----------------------------------------
	
Role Table: Used to store various roles applicable for our application
	
					---------------------
					| id | role          |
					|----|---------------|
					|1   |ROLE_ADMIN     |
					|2   |ROLE_MODERATO  |
					|3   |ROLE_USER      |
					 --------------------
					
user_role Table: Used to store the relationship between the user and the role. This table is created to store the many-to-many relationship between user and role.
	
					 -------------------
					| user_id | role_id |
					|---------|---------|
					|   1     |   1     |
					|   1     |   3     |
					|   2     |   1     |
					---------------------
