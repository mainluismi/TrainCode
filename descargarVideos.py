from pytube import YouTube
from sys import argv

# Verifica si se proporcionó un argumento de línea de comandos (el enlace de YouTube)
if len(argv) != 2:
    print("Uso: python descargarVideos.py <enlace_de_youtube>")
    exit()

# Obtiene el enlace de YouTube del argumento de línea de comandos
link = argv[1]

try:
    yt = YouTube(link)
    
    print("Título: ", yt.title)
    print("Visitas: ", yt.views)

    yd = yt.streams.get_highest_resolution()
    # Asegúrate de que la ruta de descarga sea válida y usa barras invertidas dobles en Windows o barras inclinadas en otros sistemas
    yd.download('D://Fotos damian')  # Usamos barras invertidas dobles en Windows
    print("Descarga completada.")
except Exception as e:
    print("Ocurrió un error:", str(e))
