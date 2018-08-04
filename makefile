# Pasta onde estão os *.java
SRC = src
# Pasta onde os *.class serão colocados
OUT = bin
# Compilador java
JC = javac
# -sourcepath: diz ao compilador onde achar dependencias *.java
# -cp: diz ao compilador onde achar dependencias *.class
# -d: diz ao compilador onde ele deve pôr os *.class gerados
# -encoding: Diz ao compilador a codificação do código-fonte, para
#  que (no nosso caso) caracteres acentuados em strings literais
#  sejam exibidos corretamente em qualquer plataforma.
JFLAGS = -sourcepath $(SRC) -cp $(OUT) -d $(OUT) -encoding utf8
# Gera uma lista de arquivos *.java encontrados em $(SRC)
SOURCES = $(wildcard $(SRC)/*.java)

vpath %.class $(OUT)

all: Main.class

# Compila o programa novamente se algum arquivo *.java em $(SRC)
# for alterado.
Main.class: $(SOURCES)
	mkdir -p $(OUT)
	$(JC) $(JFLAGS) $(SRC)/Main.java

# apaga os arquivos *.class gerados em $(OUT)
clean:
	$(RM) $(OUT)/*.class
