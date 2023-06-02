### comando de creación de contenedor en docker para la base de datos. Y también sirve para realizar la conexión al gestor de base de datos con el cliente de base de datos de DBeaver.

- docker run --name postgres-playlist -e POSTGRES_USER=useradmin -e POSTGRES_PASSWORD=playlist12Aa! -e POSTGRES_DB=PlaylistDB -p 5432:5432 -d postgres
