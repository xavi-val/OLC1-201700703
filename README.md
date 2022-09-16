# Pseudo-Parser to Python and Golang
Proyecto 1 del curso de Organizacion de Lenguajes y Compiladores 1 de la Universidad de San Carlos de Guatemala.

#### Descripcion

Pseudo-Parser es un lenguaje de programacion para las personas que no conocen los lenguajes Python y Golang pueda aplicar sus conocimientos en pseudocódigo y utilizando la aplicación, traducir este código a Python o Golang respectivamente.


## Como usar y configurar la app.

El proyecto consta de frontend escrito en HTML SCSS y JS y una REST/API en Spring Boot en Java. El proyecto usa JFlex como analizador lexico y CUP como analisador sintactico.

1. Descargar el repositorio.
2. Tener instalado Java 17.0.2 o superior
3. Abrir el proyecto con IDE preferido (Se recomienda usar IntelliJ IDEA)
4. Descargar las dependencias del projecto en [Maven](https://youtu.be/91DamlXb7bE)
5. Correr la aplicacion *CompiladoresApplication* la cual correra el backend en el puerto 8080
6. Abrir el *index.html* para observar la interfaz. Dentro de *script.js* existe una variable que indica la direccion ip del backend, cambiarla a la direccion ip de su PC de lo contrario no funcionaran las peticiones al backend.
7. Listo para usarse.

## Esctructura del proyecto
### Backend

### src/main/

- #### /cup

	En esta carpeta encontraras dos archivos, *parser.cup* y *golang_helper.cup*

	**parser.cup:** 	Archivo encargado de generar el parser para el analisis sintactico del pseudo codigo.

	**golang_helper.cup:** Archivo encargado de generar un parser llamado *golang_helper* el cual ayuda al traductor de golang a reacomodar la sintaxis al momento de la traduccion. Este nuevo parser es utilizado en la clase *Traductor_Go* mas adelante.

- #### java/com/backend/compiladores

	**mainController:** Encargado de recibir las peticiones POST del front para iniciar la traduccion a traves del *compilerController*

	**CompiladoresApplication:** Clase principal para correr Spring boot

- #### /services
	**compilerController:** Encargado de crear el lexer, parser, el AST (Abstract Sintax Tree), traducir el codigo y determinar los errores lexicos o sintacticos que puedan surgir, anexando toda la informacion generada al objeto response, el arbol generado se guarda en la carpeta resources.

	- #### /parserPackage
		Clases para la creacion de arboles y nodos
	- #### /response
		Clase de respuesta del backend donde van la respuesta de traduccion en conjunto con el arbol y los errores.
	- #### /traductor
		Traductores del arbol al lenguaje objetivo

- #### /jflex
	Definicion de los tokens para el analisis lexico y posterior uso en el analisis sintactico

- #### /target/generated-sources
	- #### /cup
		Parser generado a partir del archivo CUP
	- #### /jflex
		Lexer generado para el analisis lexico y devolucion del codigo en forma de tokens


