## Api-Weather 
#### by Andres Escobar
El API utiliza una base de datos embebida `h2database` y se conecta a un API externa para consultar el clima
`https://api.weatherapi.com/v1/current.json` para mas información ver el archivo de configuracion del proyecto
`application.conf`
```
api-weather
 L build.sbt
 L src
    L main
       L resources
         L application.conf
```

## Estructura

Esta es la estructura básica del todo el proyecto:

```
api-weather
 L build.sbt
 L src
    L main
       L resources
         L application.conf
       L scala
         L co.com.softcaribbean.weather
           L api
           L client
             L base
             L impl
           L model
           L persistence
           L service
           L util
           L WeatherMain
```
## Dependencias
Weather Version 1.0.0 utiliza `Scala 2.13.10` dependencias tales como `Java 11` `Akka`, `Cats`, `Caffeine`, `H2`, `PlayJson`, `Sttp`, `Slick`
entre otras que se encuentran configuradas en el archivo `build.sbt`

## Ejecutar
Para ejecutar el proyecto basta con tener en cuenta la clase principal `WeatherMain.scala`y ejecutarla, la otra opcion es por consola `sbt shell`
ejecute los siguientes comandos
```sh
update
```
```sh
run
```
```sh
# El servidor inicia por la ip local y puerto ver 'application.conf'
host: 127.0.0.1 port: 9090
```
Importar el archivo en postman `Weather-API.postman_collection.json`

Los Path de los servicios son 
* `/api/weather/version`
* `/api/weather/?location=new york`
* `/api/weather/history` 