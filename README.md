# REST API Articles

This repository includes a basic REST API built with Spring framework for recruitment process.

## Run and Test

To run the application type

```
mvn spring-boot:run
```

To execute unit and acceptance tests

```
mvn test
```


## Run and Test

To do a task in right way. You have to create ```/calendars [POST]``` two calendars and after that use endpoint ```/meetings/{calendarOneId}/{calendarTwoId}/{meetingTime} [GET]```


## Endpoints

The most common HTTP status codes are returned when there is an error.


### Get articles

```
/calendars [GET]
```

Get previously added calendars.


### Add a calendar

```
/calendars [POST]

Content-Type: application/json

{
    "working_hours": {
        "start": "hh:mm",
        "end": "hh:mm"
    },
    "planned_meeting": [
    {
        "start": "hh:mm",
        "end": "hh:mm"
    },
    {
        "start": "hh:mm",
        "end": "hh:mm"
    },
    {
        "start": "hh:mm",
        "end": "hh:mm"
    },
    {
        "start": "hh:mm",
        "end": "hh:mm"
    }
]
}
```

When succeed 201 Status code and newly created calendar object are returned. Any amount of planned meetings could be added to calendar. Error and 403 Status Code will be returned if we type hour in other type than "hh:mm" or used wrong hour or minute.


### Get possible meetings

```
/meetings/{calendarOneId}/{calendarTwoId}/{meetingTime} [GET]

{calendarOneId} is id of first calendar, from wich we would like to get meetings, compare to second one which is {calendarTwoId}
{meetingTime} is time we would like to spend for a meeting
```


# Working hours

If we did not type any working hours, we will get error and 400 Status Code. Error and 400 Status Code will also be returned if we didn't type start hours or end hour.


### Database

You need to have **MySql** installed on your machine to run the API.

After creating the API database, you need to add your **MySql** root `username` and `password` in the `application.properties` file on `src/main/resource` and `url` to empty database schema. The lines that must be modified are as follows:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```