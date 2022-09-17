
if __name__ == '__main__':
	def _potenciaManual_(_base_,_exponenete_):
		_i_ = 0
		_acumulado_ = 0
		for _i_ in range (0,_exponente_-1):
			_acumulado_ = _acumulado_+_acumulado_
		print(_acumulado_)
		print(" Esta es la potencia Manual")
	def _potenciaFuncion_(_base_,_exponente_):
		_potencia_ = _base_**(_exponente_)
		return _potencia_
	def _metodo1_():
		print("estamos entrando al metodo 1")
		print(_potenciaFuncion_(3*1+4/2,3+2))
	_metodo1_()