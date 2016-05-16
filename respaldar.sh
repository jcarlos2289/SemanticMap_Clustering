#Script para enviar los .java en src hacia el repositorio de git
#By JCarlos

if test $# -lt 1 
		then 
echo "Error: Introduce el Nombre para el Commit"
else
	git add bin/
    git add src/
    git add target/
    git add pom.xml 
    git add TrianglesPoints.txt
	git commit -m $1
	git push
	echo "Iniciando respaldo del commit " $1
	echo "Respaldo Terminado " 
fi









