# Pseudo-Parser to Python and Golang
Proyecto 1 del curso de Organizacion de Lenguajes y Compiladores 1 de la Universidad de San Carlos de Guatemala.

#### Descripcion

Pseudo-Parser es un lenguaje de programacion para las personas que no conocen los lenguajes Python y Golang pueda aplicar sus conocimientos en pseudocódigo y utilizando la aplicación, traducir este código a Python o Golang respectivamente.


## Como usar y configurar la app.

El proyecto consta de frontend escrito en HTML SCSS y JS y una REST/API en Spring Boot en Java. El proyecto usar JFlex como analizador lexico y CUP como analisador sintactico.

1. Descargar el repositorio.
2. Abrir el proyecto con IDE preferido (Se recomienda usar IntelliJ IDEA)
3. Descargar las dependencias del projecto en [Maven](https://youtu.be/91DamlXb7bE)
4. Correr la aplicacion *CompiladoresApplication* la cual correra el backend en el puerto 8080
5. Abrir el *index.html* para observar la interfaz.
	5.1 Dentro de *script.js* existe una variable que indica la direccion ip del backend, cambiarla a la direccion ip de su PC de lo contrario no funcionaran las peticiones al backend.
6. Listo para usarse.

## Esctructura del proyecto
### Backend


- #### cup

	En esta carpeta encontraras dos archivos, *parser.cup* y *golang_helper.cup*

	**parser.cup:** 	Archivo encargado de generar el parser para el analisis sintactico del pseudo codigo.

	**golang_helper.cup:** Archivo encargado de generar un parser llamado *golang_helper* el cual ayuda al traductor de golang a reacomodar la sintaxis al momento de la traduccion. Este nuevo parser es utilizado en la clase *Traductor_Go* mas adelante.

- #### com.backend.compiladores

	**mainController:** Encargado de recibir las peticiones POST del front para iniciar la traduccion a traves del *compilerController*

	**CompiladoresApplication:** Clase principal para correr Spring boot

- #### services
	**compilerController:** Encargado de crear el lexer, parser, y la 

