package main 
import(
	"math"
	"fmt"
)


func main(){
	/////inicio de la traduccion

	/*
        Primer archivo de entrada para proyecto organizacion de lenguajes y compiladores 1
*/
	//seccion de declraciones de funciones de variables

	var _variable1_ int  = 5
	var _v1_,_v2_,_v3_ string  = "esta es una cadena","esta es una cadena","esta es una cadena"
	var _curso1_ string  = "olc"
	var _curso2_ string  = "olc"
	var _curso3_ string  = "olc"
	var _pi1_ int  = 3
	var _pi2_ float64  = 3.1
	var _pi3_ float64  = 3.14
	var _pi4_ float64  = 3.141
	var _anio1_ int  = 1
	var _anio2_ int  = 9
	var _anio3_ int  = 4
	var _anio4_ int  = 5
	var _variableNeg_ float64  = -5.0
	var _encabezado1_ string  = "Universidad San Carlos de Guatemala...;"
	var _encabezado2_ string  = `Escuela de Ciencias y Sistemas
Segundo semestre
`
	var _flag1_ bool  = true
	var _flag2_ bool  = false
	var _name1_ = 'f'
	var _name2_ = 'e'
	var _name3_ = 'r'
	var _name4_,_name6_ = 'n','n'
	var _name5_ = 'a'
	var _name7_ = 'd'
	var _name8_ = 'o'
	var _operaciones1Basica_ = 1+(1)
	//2 el resultado

	var _operaciones1Basica2_ = _operaciones1Basica_+_operaciones1Basica_
	//               

	var _operaciones1Intermedia_ = 15+(9*8)+200/8*3+9
	//171 el resultado

	var _operaciones1Avanzadas1_ = ((15+9)*8+200/8*3+9)
	//291 el resultado

	var _operaciones1Avanzadas2_ = math.Pow(float64(30),float64(22.2-2.2))+(2)
	var _operaciones1Avanzadas3_ = (math.Pow(float64(30),float64(2)))+(2)
	var _operaciones1Avanzadas4_ = (math.Pow(float64(30),float64(10-8+9-4*2-1)))+(2)
	var _operaciones1Avanzadas5_ = math.Pow(float64(30),float64(10-8+9-4*2-1))+(2)
	var _operaciones1Avanzadas6_ = (5*8)%(1+5+6)
	//4 es el resultado

	var _operacionRela3_ = _operaciones1Basica_>8
	var _operacionRela3_ = (_operaciones1Basica_+6)>=8
	var _operacionRela3_ = (_operaciones1Basica_+6)<=8
	var _operacionRela4_ = _operaciones1Basica_==8
	var _operacionRela5_ = _operaciones1Basica_==_operaciones1Basica_
	var _operacionRela6_ = _operaciones1Basica_==_operaciones1Basica_+1
	var _operacionRela7_ = _operaciones1Basica_==(_operaciones1Basica_)*(8+5)
	var _operacionRela5_ = _operaciones1Basica_!=_operaciones1Basica_
	//seccion de asignaciones

	_v1_ = "esta es la cadena numero 1"
	_v2_ = "estas cadenas deben ser diferentes"
	_v3_ = "estas cadenas deben ser diferentes"
	_curso1_ = "Organizacion de lenguajes y compiladores 1"
	_curso2_ = "Organizacion de lenguajes y compiladores 1"
	_curso3_ = "Organizacion de lenguajes y compiladores 1"
	fmt.Println(_encabezado1_)
	fmt.Println(_encabezado2_)
	fmt.Print("...")
	fmt.Print(_anio1_)
	fmt.Print(_anio2_)
	fmt.Print(_anio3_)
	fmt.Print(_anio4_)
	fmt.Println(".")
	fmt.Println((_v3_))
	if _v1_==_v2_ { 
		fmt.Println("Al parecer no funciona la asignacion")
		for true {
			if !(!(_variable1_>=5*5+8/2)){
				break
			}
			fmt.Print(_variable1_)
			_variable1_ = _variable1_+1
		}
	}
	if _v1_==_v2_ { 
		fmt.Println("no tiene que imprimir este mensaje")
	} else {
		fmt.Print("este print es un ejemplo")
	}
	if _v1_==_v2_ { 
		fmt.Println("no tiene que imprimir este mensaje")
	} else if _v1_==13 { 
		fmt.Println("mensaje de prueba")
	} else if _v1_==14 { 
		fmt.Println("mensaje de prueba")
	} else {
		fmt.Println("este print es un ejemplo")
	}
	var _varB_ bool  = false
	if _varB_ { 
		fmt.Println("Estas definiendo bien los valores")
		var _varaux_ = _variable1_%2
		switch _varaux_{
			case 0 : 
				fmt.Println("el valor es mayor a 0 y menos a 2")
			case 2 : 
				fmt.Println("el valor es mayor a 2")
		}
	}
	/*Ahora empezamos con las funciones y procedimientos*/
	_potenciaManual_:= func(_base_ int,_exponente_ int){
		var _i_ int  = 0
		var _acumulado_ int  = 0
		for _i_ :=0;_i_<_exponente_-1;_i_++ {
			_acumulado_ = _acumulado_+_acumulado_
		}
		fmt.Print(_acumulado_)
		fmt.Println(" Esta es la potencia Manual")
	}
	_potenciaFuncion_:= func(_base_ int,_exponente_ int)  int {
		var _potencia_ = math.Pow(float64(_base_),float64(_exponente_))
		return _potencia_
	}
	_metodo1_:= func(){
		fmt.Println("estamos entrando al metodo 1")
		_potenciaManual_(3*1+4/2,3+2)
		fmt.Print(_potenciaFuncion_(3*1+4/2,3+2))
		fmt.Println(" Esta es la potencia Funcion")
	}
	_metodo1_()
}