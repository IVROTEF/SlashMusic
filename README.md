# SlashMusic

Red social donde los usuarios pueden compartir sus creaciones musicales.

## Conexion a la base de datos

Para conectar la base de datos en MySQL con Spring se tienen que almacenar las credenciales en variables de entorno

### Linux
```bash
$ export <VAR> = "<value>"
```

### Windows
```bash
$ setx <VAR> "<value>"
```

Donde `<VAR>` debe ser

* `SPRING_DATASOURCE_URL`: la direccion del puerto de la base de datos que en nuestro caso es `jdbc:mysql://localhost:3306/<db_name>`

* `SPRING_USERNAME`: usuario de la base de datos.

* `SPRING_PASS`: contraseña del usuario de la base de datos.

## Ejecucion

Instala las dependencias
```bash
$ mvn install
```

Compila

```bash
$ mvn compile
```

Corre el programa

```bash
$ mvn spring-boot:run
```
El programa se encuentra en `localhost:8080/`
