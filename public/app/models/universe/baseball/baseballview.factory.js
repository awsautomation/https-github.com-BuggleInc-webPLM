(function ()
{
	'use strict';
	angular
		.module('PLMApp')
		.factory('BaseballView', BaseballView);

	function BaseballView()
	{
		var ctx;
		var canvasWidth;
		var canvasHeight;
		
		var INVADER_SPRITE_SIZE = 11;
		var INVADER_SPRITE = 
			[
				[ 0,0,0,0,0,0,0,0,0,0,0 ],
				[ 0,0,1,0,0,0,0,0,1,0,0 ],
				[ 0,0,0,1,0,0,0,1,0,0,0 ],
				[ 0,0,1,1,1,1,1,1,1,0,0 ],
				[ 0,1,1,0,1,1,1,0,1,1,0 ],
				[ 1,1,1,1,1,1,1,1,1,1,1 ],
				[ 1,0,1,1,1,1,1,1,1,0,1 ],
				[ 1,0,1,0,0,0,0,0,1,0,1 ],
				[ 0,0,0,1,1,0,1,1,0,0,0 ],
				[ 0,0,0,0,0,0,0,0,0,0,0 ],
				[ 0,0,0,0,0,0,0,0,0,0,0 ],
			] ;

		var service = {
			draw: draw,
		};

		return service;

		function initUtils(canvas, baseballWorld)
		{
			ctx = canvas.getContext('2d');
			canvasWidth = canvas.width;
			canvasHeight = canvas.height;
		}

		function draw(canvas, baseballWorld)
		{
			initUtils(canvas, baseballWorld);
			ctx.beginPath();
			drawField(baseballWorld);
			drawText(baseballWorld);
			ctx.closePath();
		}

		function toRad(angle)
		{
			return angle * (Math.PI / 180);
		}

		function computeAngle(amountSides)
		{
			return (180 * amountSides - 360) / (amountSides * 2);
		}


		function drawText(baseballWorld)
		{
		ctx.beginPath();

		ctx.fillStyle = "#FFFFFF";

		ctx.font = "15px sans-serif";
		if(baseballWorld.moveCount <= 1)
			ctx.fillText(baseballWorld.moveCount+" Move",5,25);
		else ctx.fillText(baseballWorld.moveCount+ " Moves",5,25);
			

		ctx.closePath();
		}



		function drawField(baseballWorld)
		{
			var nb = baseballWorld.baseAmount;
			var radius = canvasHeight / 2;

			//Firstly, we need a circle where take place the left top corner of our rectangles
			var dif = (radius-75) + (nb * 3);

			var dif2 = (radius-50) + (nb * 2);
			
			var height = 91 - (4 * nb);
			var width = 118 - (4 * nb);

			//we need a circle divided by the double number of bases
			var angle = toRad(360 / (nb * 2));

			var angleCircle = toRad(360 / (baseballWorld.posAmount * nb * 5));

			var angleRotation = angle / nb;

			var firstX;
			var firstY;
			var circleX;
			var circleY;

			var XCenter = canvasWidth /2;
			var YCenter = radius;

			var dX1;
			var dY1;

			var dX2;
			var dY2;

			var dX3;
			var dY3;

			var dX4;
			var dY4;

			var dX5;
			var dY5;

			var unknownX;
			var unknownY;

			var x ;
			var y ;

			var ax, ay, bx, by, cx, cy;

			var arrowX;
			var arrowY;

			var distance, lambda, next ;
			var ArrDistance, ArrLambda, ArrNext;

			var buggleX;
			var buggleY;

			var indexColor = baseballWorld.oldMove;

			firstX = canvasWidth / 2 - 45;
			firstY = canvasHeight / 2 + dif - 10;

			circleX = canvasWidth /2 + 20;
			circleY = canvasHeight /2  + dif2;

			var secondX = canvasWidth / 2 - 60;
			var secondY = canvasHeight -11;

			var Fmemo = [];
			var Smemo = [];

			ctx.beginPath();
			ctx.fillStyle = "#006600";
			ctx.fillRect(0,0,canvasWidth,canvasHeight);
			ctx.closePath(); 

			
			ctx.beginPath();
			ctx.fillStyle = "#663300";
			ctx.arc(canvasWidth /2 ,canvasHeight /2, canvasWidth / 2 ,2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();

			ctx.beginPath();
			ctx.strokeStyle = "rgb(0,0,0)";
			ctx.arc(canvasWidth /2 ,canvasHeight /2, canvasWidth / 2 ,2 * Math.PI, false);
			ctx.stroke();
			ctx.closePath(); 


/*
			//draws a circle with a diam = diam - height
			ctx.beginPath();
			ctx.fillStyle = "#000000";
			ctx.arc(canvasWidth /2 ,canvasHeight /2, dif  ,2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();  */

			console.log("********************************************************");
			for(var i=0; i<nb*2; i++)
			{

					dX1 = firstX - XCenter;
					dY1 = firstY - YCenter;

					dX3 = secondX - XCenter;
					dY3 = secondY - YCenter;
					 
					dX2 = dX1 * Math.cos(angle) - dY1 * Math.sin(angle);
					dY2 = dX1 * Math.sin(angle) + dY1 * Math.cos(angle); 

					dX4 = dX3 * Math.cos(angle) - dY3 * Math.sin(angle);
					dY4 = dX3 * Math.sin(angle) + dY3 * Math.cos(angle);

					ctx.beginPath();
					ctx.moveTo(secondX,secondY);
					Smemo[0] = secondX ;
					Smemo[1] = secondY ;
					secondX = dX4 + XCenter;
					secondY = dY4 + YCenter;
					ctx.lineTo(secondX, secondY);
					Fmemo[0] = firstX ;
					Fmemo[1] = firstY ;
					firstX = dX2 + XCenter;
					firstY = dY2 + YCenter;
					ctx.lineTo(firstX, firstY);				
					//found the middle of two diagonals
					unknownX = (Smemo[0] + firstX) / 2 ;
					unknownY = (Smemo[1] + firstY) / 2 ;

					x = 2 * unknownX - secondX ;
					y = 2 * unknownY - secondY ;
					ctx.lineTo(x, y);
					ctx.lineTo(Smemo[0], Smemo[1]);


					ctx.fillStyle = baseballWorld.colors[i/2];

					if(i % 2 === 0)
					ctx.fill();
					


					ctx.closePath();

					

					//middle to middle
					if(i % 2 === 0)
					{
						ctx.beginPath();
						unknownX = (Smemo[0] + x) / 2;
						unknownY = (Smemo[1] + y) / 2;
						ctx.moveTo(unknownX,unknownY);
						unknownX = (firstX + secondX) / 2;
						unknownY = (firstY + secondY) / 2;
						ctx.lineTo(unknownX, unknownY);
						ctx.strokeStyle = "#000000";
						ctx.stroke();
						ctx.closePath();


						ctx.beginPath();
							
							ax = (Smemo[0] + x) / 2 ;
							ay = (Smemo[1] + y) / 2 ;

							bx = (firstX + secondX) / 2 ;
							by = (firstY + secondY) / 2 ;


							
							ArrDistance = Math.sqrt(Math.pow(ax-x,2)+Math.pow(ay-y,2)) ;
							ArrLambda = ArrDistance / (Math.sqrt(Math.pow(ax-x,2)+Math.pow(ay-y,2)));

							distance = Math.sqrt(Math.pow(bx-ax,2)+Math.pow(by-ay,2)) / (baseballWorld.posAmount*2) ;
							lambda = distance / (Math.sqrt(Math.pow(bx-ax,2)+Math.pow(by-ay,2))); 
	
							cx = ax + (lambda * (bx - ax));
							cy = ay + (lambda * (by - ay));

					
  
							
							next = distance	;				
						for(var j=0;j<baseballWorld.posAmount;j++)
						{
							if((i/2)*baseballWorld.posAmount+j === baseballWorld.move)
								{ 
									ctx.beginPath();
									console.log("index :", indexColor);
										ctx.strokeStyle = baseballWorld.colors[baseballWorld.field[indexColor]];
										console.log("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
									
									console.log(baseballWorld.field);
									console.log("couleur :", baseballWorld.field[indexColor]);
									//console.log("index :", baseballWorld.move);
									ctx.moveTo(cx + (ArrLambda * (x - ax)), cy + (ArrLambda * (y - ay)));
									//ctx.arcTo(cx,cy,baseballWorld.holeX,baseballWorld.holeY,200);
									ctx.lineTo(baseballWorld.holeX, baseballWorld.holeY);
									//console.log((i/2)*baseballWorld.posAmount+j);
									console.log("index :", indexColor);
									ctx.stroke();
									ctx.closePath();		
								}
								

							if(baseballWorld.field[(i/2)*baseballWorld.posAmount+j] === -1)
								{
									baseballWorld.holeX = cx + (ArrLambda * (x - ax));
									baseballWorld.holeY = cy + (ArrLambda * (y - ay));
								} 
							
							
							

							ctx.beginPath();
							ctx.fillStyle = "#FFFFFF" ;
							ctx.arc(cx, cy, 39-(5*baseballWorld.posAmount)-(1.65*(nb-2)), 2 * Math.PI, false );
							ctx.fill();
							ctx.closePath();



							

							ctx.beginPath();


							if(baseballWorld.field[(i/2)*baseballWorld.posAmount+j] != i/2 && baseballWorld.field[(i/2)*baseballWorld.posAmount+j]  != -1 )
							{
								ctx.strokeStyle = "#FF0000";
								ctx.lineWidth = 3;
							}
							else
							{
								ctx.strokeStyle = "#000000";
								ctx.lineWidth = 1;
							}


							ctx.arc(cx, cy, 39-(5*baseballWorld.posAmount)-(1.65*(nb-2)), 2 * Math.PI, false );
							ctx.stroke();

							ctx.closePath();

							ctx.lineWidth = 1;

							next += distance * 2 ;
							if(baseballWorld.field[(i/2)*baseballWorld.posAmount+j] != -1)
							{
								ctx.beginPath();


								var dx, dy;

								var pixW = 5.3-(0.6*baseballWorld.posAmount)-(2.5*(nb)) / INVADER_SPRITE_SIZE;
								var pixY = 5.3-(0.6*baseballWorld.posAmount)-(2.5*(nb)) / INVADER_SPRITE_SIZE;

								/*
								var padX = 0.5*(1)* 39-(5*baseballWorld.posAmount)-(1.65*(nb-2));
								var padY = 0.5*(1)* 39-(5*baseballWorld.posAmount)-(1.65*(nb-2)); */

								/*
								var ox = buggle.x * cellWidth;
								var oy = buggle.y * cellHeight; */

								ctx.fillStyle = baseballWorld.colors[baseballWorld.field[(i/2)*baseballWorld.posAmount+j]] ;
								
								buggleX = cx - 20 + (1.2*(nb-4)) + (3 * baseballWorld.posAmount-2);
								buggleY = cy - 19 + (1.1*(nb-4)) + (3 * baseballWorld.posAmount-2);

								for(dy=0; dy<INVADER_SPRITE_SIZE; dy++) {
									for(dx=0; dx<INVADER_SPRITE_SIZE; dx++) {
										if(INVADER_SPRITE[dy][dx] === 1) {
											ctx.fillRect(buggleX+dx*pixW, buggleY+dy*pixY, pixW, pixY);
										}
									}
								}

								ctx.closePath();

							}
							
							
							lambda = next / (Math.sqrt(Math.pow(bx-ax,2)+Math.pow(by-ay,2)));
							cx = ax + (lambda * (bx - ax));
							cy = ay + (lambda * (by - ay));

								
						}

					}

					

					/*

					for (var j = 0; j<baseballWorld.posAmount; j++) {

							ctx.beginPath();
							ctx.fillStyle = "#FFFFFF";
							ctx.arc(width / (baseballWorld.posAmount * 2) + (width / baseballWorld.posAmount * (j)) , height / 2, 30 - (5*baseballWorld.posAmount) ,2 * Math.PI, false);
							ctx.fill();
							ctx.closePath();
					};  */

			}
/*

			

			
			/*
			ctx.beginPath();
			ctx.strokeStyle = "#000000";
			ctx.arc(canvasWidth / 2, canvasHeight /2, dif2, 2 * Math.PI, false);
			ctx.stroke();
			ctx.closePath();

			for(var j = 0; j<baseballWorld.posAmount * nb * 5; j++)
			{
				dX1 = circleX - XCenter;
				dY1 = circleY - YCenter;

				ctx.beginPath();
				ctx.fillStyle = "#FF00FF";
				ctx.fillRect(circleX, circleY,10,10);
				ctx.closePath();

				dX2 = dX1 * Math.cos(angleCircle) - dY1 * Math.sin(angleCircle);
				dY2 = dX1 * Math.sin(angleCircle) + dY1 * Math.cos(angleCircle); 

				circleX = dX2 + XCenter ;
				circleY = dY2 + YCenter ;


			}
	
			*/

			/*
			ctx.beginPath();
			ctx.fillStyle = "#66FF33";
			ctx.fillRect(canvasWidth / 3, canvasHeight - 84, canvasWidth / 3, canvasHeight / 6);
			ctx.closePath();
			ctx.beginPath();
			ctx.strokeStyle = "rgb(0,0,0)";
			ctx.strokeRect(canvasWidth / 3, canvasHeight - 84 , canvasWidth / 3, canvasHeight / 6);
			ctx.closePath();

			ctx.beginPath();
			ctx.fillStyle = "#FF00FF";
			ctx.fillRect(canvasWidth / 3, 15, canvasWidth / 3, canvasHeight / 6);
			ctx.closePath();
			ctx.beginPath();
			ctx.strokeStyle = "rgb(0,0,0)";
			ctx.strokeRect(canvasWidth / 3, 15, canvasWidth / 3, canvasHeight / 6);
			ctx.closePath();

			ctx.beginPath();
			ctx.fillStyle = "#FFFF00";
			ctx.fillRect(15, canvasHeight / 3, canvasWidth / 6, canvasHeight / 3);
			ctx.closePath();
			ctx.beginPath();
			ctx.strokeStyle = "#000000";
			ctx.strokeRect(15, canvasHeight / 3, canvasWidth / 6, canvasHeight / 3);
			ctx.closePath();

			ctx.beginPath();
			ctx.fillStyle = "#0000FF";
			ctx.fillRect(canvasWidth - 84, canvasHeight / 3, canvasWidth / 6, canvasHeight / 3 );
			ctx.closePath();
			ctx.beginPath();
			ctx.strokeStyle = "#000000";
			ctx.strokeRect(canvasWidth - 84, canvasHeight / 3, canvasWidth / 6, canvasHeight / 3 );
			ctx.closePath();





			ctx.beginPath();
			ctx.fillStyle = "#FFFFFF";
			ctx.arc(canvasWidth /3 + canvasWidth / 9 -10 ,(canvasHeight / 6 * 4.8) + (canvasHeight / 6 * 4.8)/9, 30 ,2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();

			ctx.beginPath();
			ctx.fillStyle = "#FFFFFF";
			ctx.arc(canvasWidth /3 + canvasWidth / 4, (canvasHeight / 6 * 4.8) + (canvasHeight / 6 * 4.8)/9 ,30, 2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();

			*/
		
		}





	};


})();