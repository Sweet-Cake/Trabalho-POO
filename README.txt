Trabalho de programa��o orientada a objetos da FATEC Zona Leste

Este � um jogo de breakout, seu objetivo � destruir todos os tijolos e n�o deixar a bola cair.
Para executar o jogo, coloque o projeto MagicBreak no eclipse e execute a classe Main.
O jogo come�a com uma velocidade, mas ao completar-lo voc� ir� para uma segunda, mais r�pida e com mais blocos. Cuidado, perder o jogo significa voltar a velocidade inicial!
PAra iniciar o jogo, pressione ou a tecla esquerda ou a direita.
O gr�fico foi feito utilizando Graphics e Graphics2D, ent�o visualmente � bem simples.
O jogo tem tr�s classes: a Main, que ir� construir a janela do jogo, a Gameplay, onde o jogo ser� construido e ir� funcionar, e a MapGenerator, que ir� gerar os tijolos que ir�o aparecer no jogo.
Na classe Gameplay, desenhamos os objetos do jogo, al�m de criar a plataforma e as bordas. Para a bola, ela ir� inverter a dire��o caso chegue a um dos extremos de X e Y, na plataforma controlada pelo jogador, ou em um tijolo, assim "quicando".
