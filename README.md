# matrix-solution API

First init the API:

	gradle clean build
	gradle bootRun

For see documentation visit the swagger UI: 
	
	http://localhost:8080/swagger-ui.html#/

# Coverage

The API also have 96 percent of test coverage. Feel free to run the sonar task and see this detail in your sonar server:

	gradle clean build
	gradle jacocoTestReport
	gradle sonarqube
	
![alt text](https://liam-adrian.s3-sa-east-1.amazonaws.com/coverage-back.png)