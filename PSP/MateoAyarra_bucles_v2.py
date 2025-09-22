'''
Realizar programas en Python que realice las siguientes instrucciones:

Versión 1

Pedir de uno en uno 6 números enteros en pantalla
Contar cuántos son pares y cuantos impares.
Sumar los pares y hallar el producto de los impares
Presentar en pantalla:
   número de elementos par: ... // Suma:
   número de elementos impar: ... // Producto:

Versión 2: Pedir números enteros en pantalla hasta que introduzcas -1, momento en el cual se presentan los resultados en pantalla y se acaba el bucle.

Subir un fichero por cada programa que se llame: nombreApellido1_bucles_v1.py y *v2*
'''

import random as rd

numeros = []
nPares = 0
sumaPares = 0
productoImpares = 1
continuar = True

while continuar:
    num = int(input('Introduzca un numero (-1 para terminar): '))
    if num != -1:
        numeros.append(num)
    else:
        continuar = False

# Buscar como iterar en listas
for i in range (len(numeros)):
    n = numeros[i]
    if n % 2 == 00:
        nPares += 1
        sumaPares += n
    else:
        productoImpares*=n

print(nPares)
print(sumaPares)
print(productoImpares)

print(numeros)