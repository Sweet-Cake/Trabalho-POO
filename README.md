# Trabalho-POO

Trabalho de programação orientada a objetos da FATEC Zona Leste

Este é um jogo de breakout, seu objetivo é destruir todos os tijolos e não deixar a bola cair.

Para executar o jogo, coloque o projeto MagicBreak no eclipse e execute a classe Main.

O jogo começa com uma velocidade, mas ao completar-lo você irá para uma segunda, mais rápida e com mais blocos. Cuidado, perder o jogo significa voltar a velocidade inicial!

PAra iniciar o jogo, pressione ou a tecla esquerda ou a direita.

O gráfico foi feito utilizando Graphics e Graphics2D.

O jogo tem três classes: a Main, que irá construir a janela do jogo, a Gameplay, onde o jogo será construido e irá funcionar, e a MapGenerator, que irá gerar os tijolos que irão aparecer no jogo.

Na classe Gameplay, desenhamos os objetos do jogo, além de criar a plataforma e as bordas. Para a bola, ela irá inverter a direção caso chegue a um dos extremos de X e Y, na plataforma controlada pelo jogador, ou em um tijolo, assim "quicando".

Você precisa importar o projeto no eclipse.

Para importar, abra o eclipse, File>Import>General>Projects from Folder or Archive.
Ou arraste o conteudo da pasta scr(os packages controller e view) para um projeto existente(ou criado na hora).

Obs: Você pode segurar a tecla do teclado caso queira ir de um lado a outro, sem necessidade de apertar direita ou esquerda toda vez até chegar lá.
