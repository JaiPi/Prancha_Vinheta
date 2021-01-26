import aguiaj.iscte.Color;
import aguiaj.iscte.ColorImage;


class Prancha{
	Vinheta[][] matrizV;
	private int numTirasPrancha;
	ColorImage imgPrancha;
	private int espacamento =3;
	private Vinheta vinPredefinida;
	
	Prancha(int ntiras){
		if (ntiras == 0)
			throw new IllegalArgumentException("A prancha não pode ter zero tiras.");
		ColorImage imgAux = new ColorImage(98, 48);
		vinPredefinida = new Vinheta(imgAux, 1, 1);
		numTirasPrancha = ntiras;
		matrizV = new Vinheta[ntiras][1];
		for (int t =0; t < ntiras; t++){
			matrizV[t][0] = vinPredefinida;
		}
		actualizarImgPrancha();
	}

	int getLarguraPrancha(){
		return imgPrancha.getWidth();
	}
	
	int getAlturaPrancha(){
		return imgPrancha.getHeight();
	}

	private void AdicionarVinhetaNaTira(int tira){
		Vinheta[] tiraAux = new Vinheta[matrizV[tira].length+1];
		for (int vin =0; vin < matrizV[tira].length; vin++){
			tiraAux[vin] = matrizV[tira][vin];
		}
		tiraAux[tiraAux.length-1] = vinPredefinida;
		matrizV[tira] = tiraAux;
		actualizarImgPrancha();
	}

	private int alturaTira(int tira){
		int maior =0;
		for (int i =0; i < matrizV[tira].length; i++){
			if (matrizV[tira][i].getAlturaVin() >= maior)
				maior = matrizV[tira][i].getAlturaVin();			
		}
		return maior;		
	}
	
	private int larguraTira(int tira){
		int largura =0;
		for (int i=0; i < matrizV[tira].length; i++){
			largura += matrizV[tira][i].getLarguraVin();
		}
		return largura;		
	}
	
	private int maiorTira(){
		int maior =0;
		for (int i =0; i < numTirasPrancha; i++){
			if (larguraTira(i) + (matrizV[i].length-1)*espacamento > maior)
				maior = larguraTira(i) + (matrizV[i].length-1)*espacamento;
		}
		return maior;
	}

	private int alturaAteTira(int tira){
		int alturaI =0;
		for (int i =0; i < tira; i++){
			alturaI += alturaTira(i);
		}
		return alturaI;
	}
	
	private int larguraAteVin(int tira, int vin){
		int larguraI =0;
		for (int i =0; i < vin ; i++){
			larguraI += matrizV[tira][i].getLarguraVin();
		}
		return larguraI;
	}
	
	private void actualizarImgPrancha(){
		int AlturaPrancha =0;
		for (int i =0; i < numTirasPrancha; i++)
			AlturaPrancha += alturaTira(i);
		ColorImage pranchaAux = new ColorImage(maiorTira(), 
				                               AlturaPrancha + (espacamento*(numTirasPrancha-1)));
		for (int tira =0; tira < numTirasPrancha; tira++){
			for (int vin =0; vin < matrizV[tira].length; vin++){
				for (int i =0; i < matrizV[tira][vin].getLarguraVin(); i++){
					for (int j =0; j < matrizV[tira][vin].getAlturaVin(); j++){
						pranchaAux.setColor(i + larguraAteVin(tira,vin) + (espacamento * vin), 
								            j + alturaAteTira(tira) + (espacamento*tira), 
								            matrizV[tira][vin].vin.getColor(i, j));
					}
				}
			}
		}
		imgPrancha = pranchaAux;
	}
	
	void AdicionarTira(){
		Vinheta[][] vaux = new Vinheta[matrizV.length+1][];
		for(int i =0; i < matrizV.length; i++){
			vaux[i] = new Vinheta[matrizV[i].length];
			for (int j =0; j < vaux[i].length; j++)
				vaux[i][j] = matrizV[i][j];
		}		
		vaux[numTirasPrancha]= new Vinheta[1];
		vaux[numTirasPrancha][0] = vinPredefinida;
		matrizV = vaux;
		numTirasPrancha++;
		actualizarImgPrancha();
	}
	
	void RetirarTira(){
		if (numTirasPrancha == 1)
			throw new IllegalArgumentException("A prancha não pode ter zero tiras.");
		Vinheta[][] matrizAux = new Vinheta[matrizV.length-1][];
		for (int tira =0; tira < matrizAux.length; tira++){
			matrizAux[tira] = new Vinheta[matrizV[tira].length];
			for (int vin =0; vin < matrizV[tira].length; vin++){
				matrizAux[tira][vin] = matrizV[tira][vin];
			}
		}
		matrizV = matrizAux;
		numTirasPrancha--;
		actualizarImgPrancha();
	}

	private void RetirarVinhetaDaTira(int tira){
		Vinheta[] tiraAux = new Vinheta[matrizV[tira].length-1];
		
		for (int i =0; i < tiraAux.length; i++){
			tiraAux[i] = matrizV[tira][i];
		}
		matrizV[tira] = tiraAux;
		actualizarImgPrancha();
	}
	
	void NumeroDeVinhetasNaTira(int n, int tira){
		int larguraAux = matrizV[tira].length;
		if (n == 0){
			throw new IllegalArgumentException("As tiras não podem ter zero vinhetas.");
		}
		if (n > matrizV[tira].length){
			for (int i =0; i < n-larguraAux; i++){
				AdicionarVinhetaNaTira(tira);
			}
		}
		if (n < matrizV[tira].length){
			for (int i =n; i < larguraAux; i++){
				RetirarVinhetaDaTira(tira);
			}
		}	
		actualizarImgPrancha();
	}

	void ColocarVinhetaNaTira(Vinheta vin, int posicao, int tira){
		matrizV[tira][posicao] = vin;
		actualizarImgPrancha();
	}
	
	void AlterarEspessuraDaMolduraDaVinhetaNaTira(int esp, int posicao, int tira){
		Vinheta vinAux = new Vinheta(matrizV[tira][posicao].DevolverImagemOriginal(), 
				                     matrizV[tira][posicao].esp, 
				                     matrizV[tira][posicao].tipo, 
				                     matrizV[tira][posicao].cor);
	vinAux.AlterarEspessuraDaMoldura(esp);
	matrizV[tira][posicao] = vinAux;
	actualizarImgPrancha();
	}

	void AlterarTipoDeMolduraDaVinhetaNaTira(int tipo, int posicao, int tira){
		Vinheta vinAux = new Vinheta(matrizV[tira][posicao].DevolverImagemOriginal(), 
				                     matrizV[tira][posicao].esp, 
				                     matrizV[tira][posicao].tipo, 
				                     matrizV[tira][posicao].cor);
	vinAux.AlterarTipoDeMoldura(tipo);
	matrizV[tira][posicao] = vinAux;
	actualizarImgPrancha();
	}
	
	void AlterarCorDaMolduraDaVinhetaNaTira(Color cor, int posicao, int tira){
		if (cor == null)
			throw new IllegalArgumentException("Tem de adicionar uma cor.");
		
		Vinheta vinAux = new Vinheta(matrizV[tira][posicao].DevolverImagemOriginal(), 
				                     matrizV[tira][posicao].esp, 
				                     matrizV[tira][posicao].tipo, 
				                     matrizV[tira][posicao].cor);
	vinAux.AlterarCorDaMoldura(cor);
	matrizV[tira][posicao] = vinAux;
	actualizarImgPrancha();
	}
	
	void AcinzentarVinhetaDaTira(int posicao, int tira){
		Vinheta vinAux = new Vinheta(matrizV[tira][posicao].DevolverImagemOriginal(), 
				                     matrizV[tira][posicao].esp, 
				                     matrizV[tira][posicao].tipo, 
				                     matrizV[tira][posicao].cor);
	vinAux.Cinza();
	matrizV[tira][posicao] = vinAux;
	actualizarImgPrancha();
	}
	
	void ColorirVinhetaNaTira(int posicao, int tira){
		Vinheta vinAux = new Vinheta(matrizV[tira][posicao].DevolverImagemOriginal(), 
				                     matrizV[tira][posicao].esp, 
				                     matrizV[tira][posicao].tipo, 
				                     matrizV[tira][posicao].cor);
	vinAux.Colorir();
	matrizV[tira][posicao] = vinAux;
	actualizarImgPrancha();
	}
	
	ColorImage DevolverPrancha(){
		return imgPrancha;
	}
	
	void DividirVinhetaNaTiraEm(int posicao, int tira, int numPartes){
		Vinheta[] tiraAux = new Vinheta[matrizV[tira].length + numPartes -1];
		int larguraParcial = (matrizV[tira][posicao].DevolverImagemOriginal().getWidth())/numPartes;
		ColorImage imgAux = new ColorImage(larguraParcial, 
				                           matrizV[tira][posicao].DevolverImagemOriginal().getHeight());
		for (int d =0; d < numPartes; d++){
			for (int i =0; i < larguraParcial; i++){
				for (int j =0; j < matrizV[tira][posicao].DevolverImagemOriginal().getHeight(); j++){
					imgAux.setColor(i, j, 
							        matrizV[tira][posicao].DevolverImagemOriginal().getColor(
							        		i + (d*larguraParcial), j));
					}
				}
				Vinheta vinAux = new Vinheta(imgAux, 
						                     matrizV[tira][posicao].esp, 
						                     matrizV[tira][posicao].tipo, 
						                     matrizV[tira][posicao].cor);
				tiraAux[posicao + d] = vinAux;
		}
		for (int i =0; i < posicao; i++){
			tiraAux[i] = matrizV[tira][i];
		}
		for (int i = posicao + numPartes; i < tiraAux.length; i++){
			tiraAux[i] = matrizV[tira][i - numPartes +1];
		}
		matrizV[tira] = tiraAux;
		actualizarImgPrancha();
	}
	
	ColorImage DevolverVinhetaNaTira(int vin, int tira){
		return matrizV[tira][vin].vin;
	}
	
	void AlterarEspacamento(int esp){
		espacamento = esp;
		actualizarImgPrancha();
	}
	
}