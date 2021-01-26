import aguiaj.iscte.Color;
import aguiaj.iscte.ColorImage;
import java.util.Random;

class Vinheta{
	static final int SemMoldura =0;
	static final int Linha =1;
	static final int Tracejado =2;
	static final int Desenhado =3;
	private static final int Tracejado2 =4;
	private final ColorImage img;
	int esp;
	int tipo;
	Color cor;
	private int cin;
	ColorImage vin;
	
	Vinheta(ColorImage img, int esp, int tipo){
		if (img == null)
			throw new IllegalArgumentException("Tem de adicionar uma imagem à vinheta.");
		this.img = criaImg(img);
		this.vin = criaImg(img);
		
		cor = null;
		margem(esp, tipo, cor);
	}	
	
	Vinheta(ColorImage img, int esp, int tipo, Color cor){
		if (img == null)
			throw new IllegalArgumentException("Tem de adicionar uma imagem à vinheta.");
		if (cor == null)
			throw new IllegalArgumentException("Tem de adicionar uma cor à vinheta.");
		
		this.img = criaImg(img);
		this.vin = criaImg(img);
		
		margem(esp, tipo, cor);
	}	
	
	private static ColorImage criaImg(ColorImage img){
		
		ColorImage img1 = new ColorImage(img.getWidth(), img.getHeight());
				
			for (int i =0; i != img.getWidth(); i++){
				for (int j =0; j != img.getHeight(); j++)					
					img1.setColor(i, j, img.getColor(i, j));
			}
			
		return img1;	
	}

	private void margem(int esp, int tipo, Color cor){
		
		if (cor == null)
			this.cor = Color.BLACK;
		
		else
			this.cor = cor;
		
		this.esp = esp;
		this.tipo = tipo;
		
		if (tipo == 0){ 
			
			this.vin = new ColorImage(this.img.getWidth(), this.img.getHeight());
			
			for (int i =0; i != this.vin.getWidth(); i++){
				for (int j =0; j != this.vin.getHeight(); j++){

						this.vin.setColor(i, j, this.img.getColor(i, j));
				}
			}
		}
	
		else if (tipo == 1){
			
			this.vin = new ColorImage(this.img.getWidth()+this.esp*2, this.img.getHeight()+this.esp*2);
		
			for (int i =0; i != this.vin.getWidth(); i++){
				for (int j =0; j != this.vin.getHeight(); j++){
					
					if (i < esp || i >= this.img.getWidth()+this.esp)
						this.vin.setColor(i, j, this.cor);		
					
					else if (j < esp || j >= this.img.getHeight()+this.esp)
						this.vin.setColor(i, j, this.cor);
					
					else
						this.vin.setColor(i, j, this.img.getColor(i-this.esp, j-this.esp));
				}
			}
		}
		
		else if (tipo == 3){
			
			this.vin = new ColorImage(this.img.getWidth()+this.esp*2, this.img.getHeight()+this.esp*2);
			Random r = new Random();
			
			for (int i =0; i != this.vin.getWidth(); i++){
				for (int j =0; j != this.vin.getHeight(); j++){
					
					int k = r.nextInt(2);
					if (i < esp || i >= this.img.getWidth()+this.esp){
						if (k == 1)	
							this.vin.setColor(i, j, this.cor);	
					}
					
					else if (j < esp || j >= this.img.getHeight()+this.esp){
						if (k == 1)
							this.vin.setColor(i, j, this.cor);
					}
					
					else
						this.vin.setColor(i, j, this.img.getColor(i-this.esp, j-this.esp));
				}
			}
		}
		
		else if (tipo == 2){
			
			this.vin = new ColorImage(this.img.getWidth()+this.esp*2, this.img.getHeight()+this.esp*2);
			
			int h = this.img.getHeight();
			int hnq = h/this.esp;
			int hnqc = hnq/2;
			int hnqb = hnqc+1;
			int hes = h-hnqc*this.esp;
			int ph = hes/hnqb;			

			int w = this.img.getWidth();
			int wnq = w/this.esp;
			int wnqc = wnq/2;
			int wnqb = wnqc+1;
			int wes = w-wnqc*this.esp;
			int pw = wes/wnqb;
			
			
			
			for (int i =0; i != this.vin.getWidth(); i++){
				for (int j =0; j != this.vin.getHeight(); j++){
					
					if ((i < this.esp && j < this.esp) || (i < this.esp && j >= this.vin.getHeight()-this.esp) || (i >= this.vin.getWidth()-this.esp && j < this.esp) || (i >= this.vin.getWidth()-this.esp && j >= this.vin.getHeight()-this.esp))				
						this.vin.setColor(i, j, this.cor);
				}
			}
			
			
			
			for (int k = 0; k < this.esp; k++){
				for (int q = this.esp+ph; q < this.vin.getHeight()-this.esp; q += this.esp+ph){
					for (int t=0; t < this.esp; t++)
						this.vin.setColor(k, t+q, this.cor);
				}
			}			
			
			for (int k = this.vin.getWidth()-this.esp; k < this.vin.getWidth(); k++){
				for (int q = vin.getHeight()-this.esp - ph; q > 0; q -= this.esp + ph){
					for (int t=0; t < this.esp; t++)
						this.vin.setColor(k, q-t, this.cor);
				}
			}
			
			for (int k = this.esp+pw; k < vin.getWidth(); k += esp+pw){
				for (int q = vin.getHeight()-esp; q < this.vin.getHeight(); q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k+t, q, this.cor);
				}
			}
			
			for (int k = vin.getWidth()-this.esp-pw; k > 0; k -= esp+pw){
				for (int q =0; q < this.esp; q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k-t, q, this.cor);
				}
			}
			
			for (int k = this.esp+pw; k < vin.getWidth(); k += esp+pw){
				for (int q =0; q < this.esp; q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k+t, q, this.cor);
				}
			}
			
			for (int k = vin.getWidth()-this.esp; k < vin.getWidth(); k++){
				for (int q =this.esp+ph; q < vin.getHeight(); q += this.esp + ph){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k, q+t, this.cor);
				}
			}
			
			for (int k = vin.getWidth()-this.esp-pw; k > 0; k -= this.esp+pw){
				for (int q = vin.getHeight()-this.esp; q < vin.getHeight(); q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k-t, q, this.cor);
				}
			}
			
			for (int k = 0; k < this.esp; k++){
				for (int q = vin.getHeight()-this.esp-ph; q >0; q -= this.esp + ph){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k, q-t, this.cor);
				}
			}
			

				
		Colorir();
		}
		
		else if (tipo == 4){
			
			this.vin = new ColorImage(this.img.getWidth()+this.esp*2, this.img.getHeight()+this.esp*2);
			
			int h = this.img.getHeight();
			int hnq = h/this.esp;
			int hnqc = hnq/2;
			int hnqb = hnqc+1;
			int hes = h-hnqc*this.esp;
			int ph = hes/hnqb;			

			int w = this.img.getWidth();
			int wnq = w/this.esp;
			int wnqc = wnq/2;
			int wnqb = wnqc+1;
			int wes = w-wnqc*this.esp;
			int pw = wes/wnqb;
			
			
			
			for (int i =0; i != this.vin.getWidth(); i++){
				for (int j =0; j != this.vin.getHeight(); j++){
					
					if ((i < this.esp && j < this.esp) || (i < this.esp && j >= this.vin.getHeight()-this.esp) || (i >= this.vin.getWidth()-this.esp && j < this.esp) || (i >= this.vin.getWidth()-this.esp && j >= this.vin.getHeight()-this.esp))				
						this.vin.setColor(i, j, this.cor);
				}
			}
			
			
			
			for (int k = 0; k < this.esp; k++){
				for (int q = this.esp+ph; q < this.vin.getHeight()-this.esp; q += this.esp+ph){
					for (int t=0; t < this.esp; t++)
						this.vin.setColor(k, t+q, this.cor);
				}
			}			
			
			for (int k = this.vin.getWidth()-this.esp; k < this.vin.getWidth(); k++){
				for (int q = vin.getHeight()-this.esp - ph; q > 0; q -= this.esp + ph){
					for (int t=0; t < this.esp; t++)
						this.vin.setColor(k, q-t, this.cor);
				}
			}
			
			for (int k = this.esp+pw; k < vin.getWidth(); k += esp+pw){
				for (int q = vin.getHeight()-esp; q < this.vin.getHeight(); q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k+t, q, this.cor);
				}
			}
			
			for (int k = vin.getWidth()-this.esp-pw; k > 0; k -= esp+pw){
				for (int q =0; q < this.esp; q++){
					for (int t=0; t < esp; t++)
						this.vin.setColor(k-t, q, this.cor);
				}
			}		
				
		Colorir();
		}
		
		if (this.cin == 1)
			Cinza();
	}
		
	int getLarguraVin(){
		return this.vin.getWidth();
	}
		
	int getAlturaVin(){
		return this.vin.getHeight();
	}
	
	String getDimensaoDaImagem(){
		
		return "(" + this.img.getWidth() + " x " + this.img.getHeight() + ")";
	}

	void Cinza(){
		
		for (int i =0; i != this.img.getWidth(); i++){
			for (int j =0; j != this.img.getHeight(); j++){
				
				if (tipo ==0)
					this.vin.setColor(i, j, this.img.getColor(i,j).toGraytone());	
				else	
					this.vin.setColor(i+this.esp, j+this.esp, this.img.getColor(i,j).toGraytone());				
			}
		}
		this.cin = 1;
	}
		
	void Colorir(){
		
		for (int i =0; i != this.img.getWidth(); i++){
			for (int j =0; j != this.img.getHeight(); j++){
				
				if (tipo == 0)
					this.vin.setColor(i, j, this.img.getColor(i, j));
				else
					this.vin.setColor(i+this.esp, j+this.esp, this.img.getColor(i, j));
			}
		}
		
		this.cin = 0;
	}
	
	void AlterarEspessuraDaMoldura(int espe){
		margem(espe, this.tipo,this.cor);
	}

	void AlterarTipoDeMoldura(int ti){
		margem(this.esp, ti, this.cor);
	}
	
	void AlterarCorDaMoldura(Color core){
		margem(this.esp, this.tipo, core);
	}
	
	ColorImage RetornarVinheta(){
		return this.vin;
	}

	ColorImage DevolverImagemOriginal(){
		return img;
	}
	
}