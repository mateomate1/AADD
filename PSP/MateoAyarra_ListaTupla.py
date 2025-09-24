'''
Hacer un programa de adivinar un número con 2 funciones

Función main: Pide por pantalla un número entre 1 y 5 y el nombre del que intenta adivinar. Puede hacerse un número fijo de veces (5 por ejemplo) o hasta que pongas un -1, 
Genera 1 unico numero aleatorio y para cada pareja de datos creará una tupla tal como:
Si ha acertado pone la palabra "Vencedor" en el primer campo y el nombre en el 2º ("Vencedor", "Jaime") 
Si ha fallado pone el número como entero en el primer campo y el nombre en el 2º (3, "Jaime")
Añade cada tupla a una lista
Llama a la función recorre pasándole como parámetro la lista.
La función recorre la lista y analiza el primer elemento de cada tupla. Si es string saca por pantalla: "El ganador es Jaime" Si es entero, saca por pantalla: "Jaime apostó el 3 y perdió".
Al final imprime la lista
Incluir el código en el fichero nombreApellido1_ListaTupla.py y subirlo a la tarea.
'''
import random


def resultados(datos):
    for dato in datos:
        if dato[0] == 'Vencedor':
            print(f'El ganador es {dato[1]}')
        else:
            print(f'{dato[1]} apostó el {dato[0]} y perdió')

def main():
    lista_resultados = []
    continuar = True
    while continuar:
        valido = False

        numero = random.randint(1,5)
        intento = 0
        while not valido and continuar:
            intento = int(input('Introduzca un numero entre 1 y 5 (-1 para salir):'))
            if intento == -1:
                continuar = False
            else:
                if(intento >=1 and intento <=5):
                    valido = True
                else:
                    print(f'El numero {intento} no es valido')
        if continuar:
            nombre = input('Introduzca su nombre:')
            if numero == intento:
                tupla = ('Vencedor', nombre)
                lista_resultados.append(tupla)
            else:
                tupla = (intento, nombre)
                lista_resultados.append(tupla)
    resultados(lista_resultados)



if __name__ == "__main__":
    main()