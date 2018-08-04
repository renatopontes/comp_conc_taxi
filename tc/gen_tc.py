#!/usr/bin/python3

#
# Autores:
# Renato Pontes Rodrigues
# Ygor Luís M. P. da Hora
#
# Este programa gera um arquivo .txt com uma entrada para o
# programa TaxiDriver. A primeira linha do arquivo contém
# M e N separados por um espaço em branco. A linha seguinte
# contém um inteiro P. Seguem P linhas, cada uma com 4 in-
# teiros x0, y0, x e y separados por espaço. É garantido que
# o par (x0, y0) nunca será igual ao par (x, y). A próxima
# linha contém um inteiro T. Seguem T linhas, cada uma com 2
# inteiros separados por espaço.
# 
# Todos os valores são limitados de acordo com a descrição
# do trabalho. Se qualquer uma das variáveis de entrada (M,
# N, P, T) violar estes limites, o gerador se recusará a
# gerar o caso de teste.
#

import sys, random

while(True):
    
    while(True):
        print('Enter N, M, P and T all on the same line, separated by white space:')
        try:
            values = list(map(int, input().strip().split()))
            if (len(values) < 4):
                raise ValueError
            n, m, p, t = values
            if n < 4 or n > 1000 or \
               m < 4 or m > 1000 or \
               p < 1 or p > 200 or \
               t < 1 or t > 100:
                raise ValueError
               
        except ValueError:
            print('--ERROR: incorrect input. Try again(Y/n)?', end=' ')
            ans = input().strip()
            if (ans == 'n'): sys.exit()
            print()
        else: break
    
    filename = '{}_{}_{}_{}.txt'.format(n, m, p, t)
    fp = open(filename, 'w')

    fp.write('{} {}\n{}\n'.format(n, m, p))
    for i in range(0, p):   
        x0, y0 = random.randint(0, m-1), random.randint(0, n-1);
        
        x, y = x0, y0;
        
        while((x == x0 and y == y0)):
            x0, y0 = random.randint(0, m-1), random.randint(0, n-1);
        
        fp.write('{} {} {} {}\n'.format(x0, y0, x, y));

    fp.write('{}\n'.format(t))
    for i in range(0, t):
        x0, y0 = random.randint(0, m-1), random.randint(0, n-1);
        fp.write('{} {}\n'.format(x0, y0))
        
    print('\nOutput written to \'' + filename + '\'\nGenerate another(Y/n)?', end=' ')
    keepRunning = input().strip()
    if (keepRunning.lower() == 'n'): break
    print()