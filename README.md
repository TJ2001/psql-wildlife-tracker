# Wildlife_Tracker_

#### _It will list animals and their sightings, {September 30, 2016}_

#### By _**Tim Jung**_

## Description

_The purpose of this application is to create database with a table for sightings and animals. It will help rangers keep track of animal last sightings, if they had their checkup, and if a tracker has been placed._

## Setup/Installation Requirements

* _Download zip or clone files to desktop._
* _Have Gradle installed on the system._
* _Have PostgreSQL on the system._
* _Open a terminal and run command "postgres"._
* _Open another terminal or another tab in the terminal run command "psql"._
* In PSQL: Use the following commands (1) "CREATE DATABASE wildLife_tracker;"
  (2) "CREATE TABLE animals(id serial PRIMARY KEY, name varchar, type varchar, sightingid int);" 3. "CREATE TABLE sightings(id serial PRIMARY KEY, animalid int, location varchar, ranger varchar, timesighted timestamp);"
* _If you want to run the tests as well. In PSQL: "CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;"
* _Open console to the project package folder and run "gradle run" ._
* _Go to http://localhost:4567/ in a browser preferably Chrome._
* _Everything should just work fine, if directions are followed._

## Specifications

* _Input: Animals and Sightings._
* _Output: Display general view of animals on the homepage and sightings in a more detailed view of the animals._

## Known Bugs

_It's mostly bug free. Older browsers might have issues._

## Support and contact details

_Feel free to make a branch and work on it. Contact git user: TJ2001 for any issues._

## Technologies Used

_Java, Gradle, PostgreSQL are utilized for the making of this application._

### License

*This is created under CC.*

CC Copyright (c) 2016
