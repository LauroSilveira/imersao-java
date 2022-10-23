# imersao-java

This application is result of one week in immersion Java powered by Alura school from Brazil. 
Web Site: https://www.alura.com.br/imersao-java

## Summary
* [Summary](#summary)
* [About](#about)
* [Techinologies](#techinologies)
* [How to run](#how-to-run)
* [Requests](#requests)
  * [IMDB](#imdb)
  * [NASA](#nasa)
  * [MARVEL](#marvel)
* [Future Development](#future-development)
* [Contributors](#contributors)


# About

This application is result of one week in immersion Java powered by Alura school from Brazil.
Basicly this application does requets from APIs, IMBD, NASA and Marvel.

# Techinologies
- Java 17
- Lombok
- Jackson
- Spring boot 2.7.4

# How to Run
If you want to run in command line just go to the same directory of `Application.java` and run: 

```shell
java Application
```
Or Just click in Run in your favourite IDE.
# Requests

#### IMDB
This application call the API from IMDB to retrieve the 250 top movies and classify them regard in their ranking. Some the results are:
![](docs/IMDB/screenshot-top-250-popular-movies.png)

Also, another API is called to retrieve the most popular movies at the moment.
One of the results is:

![](docs/IMDB/image-thor-love-and-thunder.png)

After get all this information, this application is capable to generate Sticker with previous data.

#### NASA
Another API called is from NASA, this will retrieve the photo astronomic of the day.
So every day is a new phot from a differente planete, and believe me, is amazing!
![](docs/NASA/Milky Way and Zodiacal Light over Australian Pinnacles.png)

#### Marvel

In case of Marvel the application calls two different endpoints, all characters and all comics.
Some results from API characaters are:


## Contributors
[@LauroSilveira](https://github.com/LauroSilveira)

Fell free to fork and contribute :wink:

