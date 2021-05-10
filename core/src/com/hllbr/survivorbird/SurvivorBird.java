package com.hllbr.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;


public class SurvivorBird extends ApplicationAdapter {//Kodlarımı yazacağım alan burası

	SpriteBatch batch;
	Texture background;
	Texture bird ;
	Texture bee,bee2,bee3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0 ;
	float velocity = 2;//hız integerim
	float gravity = 0.7f;//YerÇekimim
	float enemyVelocity = 2;
	Random random ;
	int score = 0;
	int scoredEnemy = 0 ;
	BitmapFont font;
	BitmapFont font2;

	Circle birdCircle;//Çarpışmalar için

	ShapeRenderer shapeRenderer ;

	int numberOfEnemies = 4 ;
	float[] enemyX = new float[numberOfEnemies];
	float [] enemyOfSet = new float[numberOfEnemies];
	float [] enemyOfSet2 = new float[numberOfEnemies];
	float [] enemyOfSet3 = new float[numberOfEnemies];
	float distance = 0 ;



	Circle[]enemyCircles;
	Circle[]enemyCircles2;
	Circle[]enemyCircles3;



	@Override
	public void create () {
		//OnCreate'in aynısı Oyun başladığında yapılmasını istediğim işlemleri bu alana yazıyorum
		batch = new SpriteBatch();
		background = new Texture("background.png");//burada tanımlanadan değer render içerisinde çiziliyor.
		bird = new Texture("bird.png");
		bee = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() /2 ;
		random = new Random();

		birdX = Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdY = Gdx.graphics.getHeight()/3;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];//4 de yazabilirdim

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for(int i = 0; i<numberOfEnemies;i++){

			enemyOfSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOfSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOfSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);


			enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth()/2+ i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}


	}

	@Override
	public void render () {
		//Oyun devam ettiği sürece devamlı çağrılan bir metod .Sürekli olması istenen olayların senatyoları bu alana yazılacak
		//Düşmanın ekrana girmesi arıalrın uçması arkaplan hareketliliği bu alanda yazılan kodlarla sağlanacak

			//Oyunun başlatılması için bir değişken oluştumam gerekiyor.

			//System.out.println("Touched");this line is for testing purposes
		batch.begin();//begin ve end arasına hangi objeleri kullanacağımı söyleyerek başlıyorum
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1 ){

			if(enemyX[scoredEnemy] <Gdx.graphics.getWidth()/2-bird.getHeight()/2){
				score++;
				if(scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;

				}else{
					scoredEnemy = 0;

				}
			}
			if(Gdx.input.justTouched()){
				velocity = -7;
			}
			for(int i = 0;i<numberOfEnemies;i++){
				if(enemyX[i]< -Gdx.graphics.getWidth()/15){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOfSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

				}else{
					enemyX[i] = enemyX[i]-enemyVelocity;

				}

				batch.draw(bee,enemyX[i],Gdx.graphics.getHeight()/2+enemyOfSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOfSet2[i]	,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOfSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircles[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles2[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles3[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			}

			if(birdY>0 ){
				velocity= velocity+gravity;
				birdY = birdY-velocity;//(birdY= velocity)uçuruyor :)
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){

			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){
			font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight()/2);
			if(Gdx.input.justTouched()){
				gameState = 1;
				birdY = Gdx.graphics.getHeight()/3;

				for (int i = 0; i<numberOfEnemies; i++) {


					enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}
				velocity = 0;
				scoredEnemy = 0 ;
				score = 0 ;


			}
		}

		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdCircle.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/25);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for(int i = 0 ;i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

		if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])){
			gameState = 2 ;
			System.out.println("touched");
		}


		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		/*batch.dispose();
		img.dispose();*/
	}
}
