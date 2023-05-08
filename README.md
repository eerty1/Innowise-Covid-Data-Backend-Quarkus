# Innowise-Covid-Data-Backend-Quarkus

Backend Java Quarkus application to consume and process API 

## Task Requirements

Разработать и имплементировать API, который позволяет найти для указаннного списка стран:

* Максимальное и минимальное число новых случаев в день / за указанный период.

* Данные получать из [API](https://covid19api.com)

Использовать: **Quarkus** и **GraalVM**

Peculiarities:

* Since the source API requires subscription, some problems can appear, specifically "429 TooManyRequests";

* Source API provides some faulty/broken/empty statistics on particular countries 

## Installation

You are proposed to use GraalVM on 17th Java

Current Maven version.

![This is an image](https://i.ibb.co/5M5bxcm/image.png)

There're the steps to unpack my project: 

* git clone Innowise-Covid-Data-Backend-Quarkus

* Open it via your IDE

