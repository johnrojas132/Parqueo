1. Introducción
Este documento describe el desarrollo del Sistema de Control de Parqueo, una aplicación de escritorio en Java que permite registrar, buscar, actualizar, eliminar y listar vehículos dentro de un parqueo, con persistencia de datos en una base de datos relacional. El proyecto se desarrolló en equipo, dividiendo el trabajo en cuatro capas independientes: conexión y modelo, datos, negocio, y presentación.
Objetivo
Aplicar los conceptos de programación orientada a objetos, manejo de excepciones, acceso a bases de datos mediante JDBC y construcción de interfaces gráficas con Swing, distribuyendo el trabajo entre los integrantes del equipo bajo una arquitectura de software por capas.
Nota sobre el motor de base de datos
El enunciado original del proyecto especificaba el uso de SQL Server (base de datos ParqueoDB, tabla Vehiculo, driver mssql-jdbc, administrado desde SQL Server Management Studio 22). El equipo decidió implementar el proyecto sobre MySQL (base de datos sistema_parqueo, tabla Parqueo, driver mysql-connector-j), manteniendo exactamente la misma arquitectura, capas y responsabilidades descritas en la guía original. El cambio de motor de base de datos no afecta la lógica de negocio, las validaciones ni la interfaz gráfica, ya que la capa de conexión (ConexionBD.java) aísla al resto del sistema del motor utilizado.
2. Arquitectura del sistema
El sistema está organizado en cuatro capas, cada una con una responsabilidad única:
●	Presentación: captura los datos del usuario y muestra los resultados (MainFrame, App). No valida datos ni accede a la base de datos directamente.
●	Negocio: valida los datos y aplica las reglas del sistema (VehiculoNegocio, NegocioException).
●	Datos (DAO): ejecuta las sentencias SQL contra la base de datos (VehiculoDAO).
●	Conexión y modelo: provee la conexión JDBC y la clase de datos Vehiculo (ConexionBD, Vehiculo).
El flujo de dependencias entre capas es el siguiente: MainFrame se comunica con VehiculoNegocio, que a su vez se comunica con VehiculoDAO, que a su vez se comunica con ConexionBD para llegar a la base de datos MySQL.
La interfaz gráfica nunca contiene sentencias SQL ni lógica de validación: toda petición del usuario pasa primero por la capa de negocio, que valida y, si los datos son correctos, delega en la capa de datos.
3. División del trabajo en equipo
El proyecto se dividió en cuatro partes de complejidad equivalente, una por integrante, siguiendo la arquitectura por capas descrita arriba.
Integrante 1 — Base de datos y capa de Conexión/Modelo
Archivos a cargo: crear_sistema_parqueo.sql, ConexionBD.java, Vehiculo.java.
Tareas: crear y probar la base de datos, verificar el driver JDBC, y documentar la configuración de conexión.
Pruebas: ejecutar el script y confirmar la creación de la tabla; probar ConexionBD.obtenerConexion() con una clase main de prueba.
Integrante 2 — Capa de Datos (DAO)
Archivos a cargo: VehiculoDAO.java, PruebaVehiculoDAO.java.
Tareas: implementar y probar los métodos agregar(), listar(), buscarPorId(), actualizar() y eliminar() contra la base de datos real.
Pruebas: insertar, listar, actualizar y eliminar un registro de prueba desde una clase main temporal, confirmando los cambios en la base de datos.
Integrante 3 — Capa de Negocio
Archivos a cargo: VehiculoNegocio.java, NegocioException.java.
Tareas: implementar las validaciones (placa, teléfono numérico, espacio numérico, campos obligatorios) y las reglas de negocio (no duplicar placas, no actualizar ni eliminar placas inexistentes).
Pruebas: probar cada validación con datos inválidos (placa vacía, teléfono con letras, espacio negativo) y confirmar que el mensaje de error es claro.
Integrante 4 — Capa de Presentación e integración final
Archivos a cargo: MainFrame.java, App.java.
Tareas: construir el menú, la barra de herramientas, las pestañas Registro y Lista, y la tabla de vehículos; conectar los eventos de la interfaz con la capa de negocio; integrar el trabajo de los cuatro integrantes en un solo proyecto funcional.
Pruebas: probar el flujo completo (registrar, buscar, actualizar, eliminar, listar) desde la interfaz gráfica, y confirmar que no hay errores al cerrar la aplicación.
Integración final: una vez que cada integrante subió su parte, el Integrante 4 realizó el merge de todos los paquetes en un solo proyecto de NetBeans y verificó que compilara y funcionara de principio a fin antes de la entrega.
4. Funcionalidades implementadas
●	Registrar un nuevo vehículo, con validación de placa, propietario, teléfono, número de espacio y tipo de vehículo.
●	Buscar un vehículo por placa, desde la barra de herramientas o desde la pestaña Lista.
●	Actualizar los datos de un vehículo existente.
●	Eliminar un vehículo, con confirmación previa del usuario.
●	Listar todos los vehículos registrados en una tabla.
●	Cargar un vehículo seleccionado de la tabla al formulario con doble clic.
●	Confirmación antes de cerrar la aplicación.
●	Mensajes claros de error o éxito mediante cuadros de diálogo.
5. Manual de instalación y ejecución
1.	Crear un proyecto Java Application en NetBeans llamado ProyectoParqueo.
2.	Copiar la carpeta src/parqueo completa dentro del src del proyecto, respetando los paquetes (conexion, modelo, excepciones, datos, negocio, presentacion, main).
3.	Agregar el driver JDBC de MySQL (mysql-connector-j) en Propiedades del proyecto → Librerías → Agregar JAR/Carpeta.
4.	Ejecutar el script crear_sistema_parqueo.sql en el servidor MySQL para crear la base de datos y la tabla.
5.	Editar ConexionBD.java y colocar el usuario y la contraseña reales del servidor MySQL.
6.	Ejecutar la clase parqueo.main.App (botón derecho → Run File).
6. Pruebas realizadas
Se probó el flujo completo de la aplicación de principio a fin, desde la interfaz gráfica:
●	Registrar un vehículo nuevo y confirmar el mensaje de éxito.
●	Intentar registrar una placa duplicada y confirmar el mensaje de error de negocio.
●	Buscar un vehículo por placa existente y por placa inexistente.
●	Actualizar los datos de un vehículo registrado.
●	Eliminar un vehículo, confirmando el diálogo previo.
●	Listar todos los vehículos y verificar que la tabla se actualiza tras cada operación.
●	Cerrar la aplicación y confirmar que no se generan errores ni conexiones abiertas.
7. Conclusiones
El desarrollo del Sistema de Control de Parqueo permitió al equipo aplicar de forma práctica la arquitectura por capas, separando claramente las responsabilidades de conexión, acceso a datos, lógica de negocio y presentación. La división del trabajo en cuatro partes de complejidad equivalente facilitó el desarrollo paralelo y la integración final, resultando en una aplicación funcional que cumple con los requerimientos de registro, búsqueda, actualización, eliminación y listado de vehículos.
