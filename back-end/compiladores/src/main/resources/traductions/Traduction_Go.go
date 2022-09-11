package main 
import(
	"math"
)


func main(){
	
	_potenciaFuncion_:= func(_base_ int,_exponente_ int)  int {
		var _potencia_ = math.Pow(float64(_base_),float64(_exponente_))
		return _potencia_
	}

	_potenciaFuncion_(2,2);
}