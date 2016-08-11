package game.dl.MoveableObjects;

import game.dl.Generator.World;
import game.dl.Managers.GUIManager;
import game.dl.Managers.HUDManager;
import game.dl.Managers.MouseManager;
import game.dl.gameloop.GameLoop;
import game.dl.main.Animator;
import game.dl.main.Assets;
import game.dl.main.Check;
import game.dl.main.Main;
import game.gos.main.VectorToF;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player implements KeyListener {

	static VectorToF pos;
	private World world;
	private int width = 48;
	private int height = 64;
	private int scale = 2;

	private static boolean up, down, left, right, running;

	// character movement
	private float maxSpeed = 3 * 64F;
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	private float fixedDT = 1f / 60F;
	private float slowdown = 16.04f;
	private long animationSpeed = 180;

	MouseManager playermm = new MouseManager();

// rendering
	private int renderDistanceWidth = 44;
	private int renderDistanceHeight = 28;
	public static Rectangle render;

	private int animationState = 0;

	/**
	 * 0- Up
	 * 1- down
	 * 2- Left
	 * 3- Right
	 * 4- idle
	 */

	private ArrayList<BufferedImage> listUp;
	private Animator ani_Up;
	private ArrayList<BufferedImage> listDown;
	private Animator ani_Down;
	private ArrayList<BufferedImage> listLeft;
	private Animator ani_Left;
	private ArrayList<BufferedImage> listRight;
	private Animator ani_Right;

	private ArrayList<BufferedImage> listIdle;
	private Animator ani_Idle;

	private HUDManager hudm;
	private GUIManager guim;

// private boolean mapMove = true;

	public Player() {
		pos = new VectorToF(Main.width / 2 - width / 2, Main.height / 2 - height / 2);
	}

	public void init(World world) {
		guim = new GUIManager();
		hudm = new HUDManager(this);
		this.world = world;

		render = new Rectangle((int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos
				- renderDistanceWidth * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceHeight
						* 32 / 2 + height / 2), renderDistanceWidth * 32, renderDistanceHeight * 32);

		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listIdle = new ArrayList<BufferedImage>();

		listUp.add(Assets.player.getTile(252, 334, 32, 50));
		listUp.add(Assets.player.getTile(284, 334, 32, 50));
		listUp.add(Assets.player.getTile(317, 334, 32, 50));
		listUp.add(Assets.player.getTile(350, 334, 32, 50));

		listDown.add(Assets.player.getTile(252, 183, 32, 50));
		listDown.add(Assets.player.getTile(284, 183, 32, 50));
		listDown.add(Assets.player.getTile(317, 183, 32, 50));
		listDown.add(Assets.player.getTile(350, 183, 32, 50));

		listLeft.add(Assets.player.getTile(252, 234, 32, 50));
		listLeft.add(Assets.player.getTile(284, 234, 32, 50));
		listLeft.add(Assets.player.getTile(317, 234, 32, 50));
		listLeft.add(Assets.player.getTile(350, 234, 32, 50));

		listRight.add(Assets.player.getTile(252, 284, 32, 50));
		listRight.add(Assets.player.getTile(284, 284, 32, 50));
		listRight.add(Assets.player.getTile(317, 284, 32, 50));
		listRight.add(Assets.player.getTile(350, 284, 32, 50));

		listIdle.add(Assets.player.getTile(0, 32, 16, 16));
		listIdle.add(Assets.player.getTile(16, 32, 16, 16));
		listIdle.add(Assets.player.getTile(32, 32, 16, 16));
		listIdle.add(Assets.player.getTile(48, 32, 16, 16));

		// up
		ani_Up = new Animator(listUp);
		ani_Up.setSpeed(animationSpeed);
		ani_Up.play();

		// down
		ani_Down = new Animator(listDown);
		ani_Down.setSpeed(animationSpeed);
		ani_Down.play();

		// left
		ani_Left = new Animator(listLeft);
		ani_Left.setSpeed(animationSpeed);
		ani_Left.play();

		// right
		ani_Right = new Animator(listRight);
		ani_Right.setSpeed(animationSpeed);
		ani_Right.play();

		// Idle
		ani_Idle = new Animator(listIdle);
		ani_Idle.setSpeed(animationSpeed);
		ani_Idle.play();

	}

	public void tick(double deltaTime) {

		playermm.tick();

		render = new Rectangle((int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos
				- renderDistanceWidth * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceHeight
						* 32 / 2 + height / 2), renderDistanceWidth * 32, renderDistanceHeight * 32);

		float moveAmountu = (float) (speedUp * fixedDT);
		float moveAmountd = (float) (speedDown * fixedDT);
		float moveAmountl = (float) (speedLeft * fixedDT);
		float moveAmountr = (float) (speedRight * fixedDT);

		// start of Map movment
		if (up) {
			moveMapUp(moveAmountu);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountu);
		}// end if(up)

		if (down) {
			moveMapDown(moveAmountd);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountd);
		}// end if(down)

		if (left) {
			moveMapLeft(moveAmountl);
			animationState = 2;
		} else {
			moveMapLeftGlide(moveAmountl);
		}// end if(left)

		if (right) {
			moveMapRight(moveAmountr);
			animationState = 3;
		} else {
			moveMapRightGlide(moveAmountr);
		}// end if(right)

		if (running) {
			if (animationSpeed != 100) {
				animationSpeed = 100;

				ani_Up.setSpeed(animationSpeed);
				ani_Down.setSpeed(animationSpeed);
				ani_Left.setSpeed(animationSpeed);
				ani_Right.setSpeed(animationSpeed);
				ani_Idle.setSpeed(animationSpeed);
				maxSpeed += 32;
			}
		} else {
			if (animationSpeed != 180) {
				animationSpeed = 180;

				ani_Up.setSpeed(animationSpeed);
				ani_Down.setSpeed(animationSpeed);
				ani_Left.setSpeed(animationSpeed);
				ani_Right.setSpeed(animationSpeed);
				ani_Idle.setSpeed(animationSpeed);
				maxSpeed -= 32;
			}
		}

		if (!up && !down && !left && !right) {
			/**
			 * idle not pressing keys
			 */
			animationState = 4;
		}

	}// end tick

	public void PlayerMove() {
// if (!mapMove) {
// // start of player movment
// if (up) {
//
// if (!Check.CollisionPlayerBlock(new Point(
// (int) (pos.xpos + GameLoop.map.xpos), (int) (pos.ypos
// + GameLoop.map.ypos - moveAmountu)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos + width),
// (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)))) {
//
// if (speedUp < maxSpeed) {
// speedUp += slowdown;
// } else {
// speedUp = maxSpeed;
// }
//
// pos.ypos -= moveAmountu;
//
// } else {
// speedUp = 0;
// }
// } else {
//
// if (!Check.CollisionPlayerBlock(new Point(
// (int) (pos.xpos + GameLoop.map.xpos), (int) (pos.ypos
// + GameLoop.map.ypos - moveAmountu)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos + width),
// (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)))) {
//
// if (speedUp != 0) {
// speedUp -= slowdown;
//
// if (speedUp < 0) {
// speedUp = 0;
// }
// }
// pos.ypos -= moveAmountu;
// } else {
// speedUp = 0;
// }
// }// end if(up)
//
// if (down) {
//
// if (!Check
// .CollisionPlayerBlock(
// new Point((int) (pos.xpos + GameLoop.map.xpos),
// (int) (pos.ypos + GameLoop.map.ypos
// + height + moveAmountd)),
//
// new Point(
// (int) (pos.xpos + GameLoop.map.xpos + width),
// (int) (pos.ypos + GameLoop.map.ypos
// + height + moveAmountd)))) {
//
// if (speedDown < maxSpeed) {
// speedDown += slowdown;
// } else {
// speedDown = maxSpeed;
// }
// pos.ypos += moveAmountd;
//
// } else {
// speedDown = 0;
// }
// } else {
//
// if (!Check
// .CollisionPlayerBlock(
// new Point((int) (pos.xpos + GameLoop.map.xpos),
// (int) (pos.ypos + GameLoop.map.ypos
// + height + moveAmountd)),
//
// new Point(
// (int) (pos.xpos + GameLoop.map.xpos + width),
// (int) (pos.ypos + GameLoop.map.ypos
// + height + moveAmountd)))) {
// if (speedDown != 0) {
// speedDown -= slowdown;
//
// if (speedDown < 0) {
// speedDown = 0;
// }
// }
// pos.ypos += moveAmountd;
// } else {
// speedDown = 0;
// }
// }// end if(down)
//
// if (left) {
//
// if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos
// + GameLoop.map.xpos - moveAmountl), (int) (pos.ypos
// + GameLoop.map.ypos + height)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl),
// (int) (pos.ypos + GameLoop.map.ypos + height)))) {
// if (speedLeft < maxSpeed) {
// speedLeft += slowdown;
// } else {
// speedLeft = maxSpeed;
// }
// pos.xpos -= moveAmountl;
// } else {
// speedLeft = 0;
// }
// } else {
// if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos
// + GameLoop.map.xpos - moveAmountl), (int) (pos.ypos
// + GameLoop.map.ypos + height)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl),
// (int) (pos.ypos + GameLoop.map.ypos + height)))) {
// if (speedLeft != 0) {
// speedLeft -= slowdown;
//
// if (speedLeft < 0) {
// speedLeft = 0;
// }
// }
// pos.xpos -= moveAmountl;
// } else {
// speedLeft = 0;
// }
// }// end if(left)
//
// if (right) {
//
// if (!Check
// .CollisionPlayerBlock(new Point((int) (pos.xpos
// + GameLoop.map.xpos + width + moveAmountr),
// (int) (pos.ypos + GameLoop.map.ypos)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos
// + width + moveAmountr), (int) (pos.ypos
// + GameLoop.map.ypos + height)))) {
//
// if (speedRight < maxSpeed) {
// speedRight += slowdown;
// } else {
// speedRight = maxSpeed;
// }
// pos.xpos += moveAmountr;
//
// } else {
// speedRight = 0;
// }
//
// } else {
//
// if (!Check
// .CollisionPlayerBlock(new Point((int) (pos.xpos
// + GameLoop.map.xpos + width + moveAmountr),
// (int) (pos.ypos + GameLoop.map.ypos)),
//
// new Point((int) (pos.xpos + GameLoop.map.xpos
// + width + moveAmountr), (int) (pos.ypos
// + GameLoop.map.ypos + height)))) {
// if (speedRight != 0) {
// speedRight -= slowdown;
//
// if (speedRight < 0) {
// speedRight = 0;
// }
// }
// pos.xpos += moveAmountr;
// } else {
// speedRight = 0;
// }
//
// }// end if(right)
//
// // end of player movement
// } else {
//
// }
	}// end playerMove()

	public void moveMapUp(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos),
				(int) (pos.ypos + GameLoop.map.ypos - speed)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width),
				(int) (pos.ypos + GameLoop.map.ypos - speed)))) {

			if (speedUp < maxSpeed) {
				speedUp += slowdown;
			} else {
				speedUp = maxSpeed;
			}

			GameLoop.map.ypos -= speed;
		} else {
			speedUp = 0;
		}
	}

	public void moveMapUpGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos),
				(int) (pos.ypos + GameLoop.map.ypos - speed)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width),
				(int) (pos.ypos + GameLoop.map.ypos - speed)))) {

			if (speedUp != 0) {
				speedUp -= slowdown;

				if (speedUp < 0) {
					speedUp = 0;
				}
			}
			GameLoop.map.ypos -= speed;
		} else {
			speedUp = 0;
		}
	}

	public void moveMapDown(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos),
				(int) (pos.ypos + GameLoop.map.ypos + height + speed)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width), (int) (pos.ypos + GameLoop.map.ypos
				+ height + speed)))) {

			if (speedDown < maxSpeed) {
				speedDown += slowdown;
			} else {
				speedDown = maxSpeed;
			}
			GameLoop.map.ypos += speed;

		} else {
			speedDown = 0;
		}
	}

	public void moveMapDownGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos),
				(int) (pos.ypos + GameLoop.map.ypos + height + speed)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width), (int) (pos.ypos + GameLoop.map.ypos
				+ height + speed)))) {
			if (speedDown != 0) {
				speedDown -= slowdown;

				if (speedDown < 0) {
					speedDown = 0;
				}
			}
			GameLoop.map.ypos += speed;
		} else {
			speedDown = 0;
		}
	}

	public void moveMapLeft(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)),

		new Point((int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)))) {

			if (speedLeft < maxSpeed) {
				speedLeft += slowdown;
			} else {
				speedLeft = maxSpeed;
			}
			GameLoop.map.xpos -= speed;
		} else {
			speedLeft = 0;
		}
	}

	public void moveMapLeftGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)),

		new Point((int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)))) {
			if (speedLeft != 0) {
				speedLeft -= slowdown;

				if (speedLeft < 0) {
					speedLeft = 0;
				}
			}
			GameLoop.map.xpos -= speed;
		} else {
			speedLeft = 0;
		}
	}

	public void moveMapRight(float speed) {
		if (!Check.CollisionPlayerBlock(new Point(
				(int) (pos.xpos + GameLoop.map.xpos + width + speed),
				(int) (pos.ypos + GameLoop.map.ypos)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed), (int) (pos.ypos
				+ GameLoop.map.ypos + height)))) {

			if (speedRight < maxSpeed) {
				speedRight += slowdown;
			} else {
				speedRight = maxSpeed;
			}
			GameLoop.map.xpos += speed;
		} else {
			speedRight = 0;
		}
	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point(
				(int) (pos.xpos + GameLoop.map.xpos + width + speed),
				(int) (pos.ypos + GameLoop.map.ypos)),

		new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed), (int) (pos.ypos
				+ GameLoop.map.ypos + height)))) {
			if (speedRight != 0) {
				speedRight -= slowdown;

				if (speedRight < 0) {
					speedRight = 0;
				}
			}
			GameLoop.map.xpos += speed;
		} else {
			speedRight = 0;
		}
	}

	public void render(Graphics2D g) {
		g.drawRect((int) pos.xpos, (int) pos.ypos, width, height);
		g.clipRect(0, 0, Main.width, Main.height);

		switch (animationState) {

		case 0:// Up
			g.drawImage(ani_Up.sprite, (int) pos.xpos - height / 2, (int) pos.ypos - width, width
					* scale, height * scale, null);
			if (up) {
				ani_Up.update(System.currentTimeMillis());
			}
			break;
		case 1:// Down
			g.drawImage(ani_Down.sprite, (int) pos.xpos - height / 2, (int) pos.ypos - width, width
					* scale, height * scale, null);
			if (down) {
				ani_Down.update(System.currentTimeMillis());
			}

			break;
		case 2:// Left
			g.drawImage(ani_Left.sprite, (int) pos.xpos - height / 3, (int) pos.ypos - width, width
					* scale, height * scale, null);
			if (left) {
				ani_Left.update(System.currentTimeMillis());
			}

			break;
		case 3:// Right
			g.drawImage(ani_Right.sprite, (int) pos.xpos - height / 2, (int) pos.ypos - width,
					width * scale, height * scale, null);
			if (right) {
				ani_Right.update(System.currentTimeMillis());
			}

			break;
		case 4: // 4 Idle
			g.drawImage(ani_Idle.sprite, (int) pos.xpos - height / 2, (int) pos.ypos - width, width
					* scale, height * scale, null);
			ani_Idle.update(System.currentTimeMillis());
			break;
		}

		g.drawRect((int) pos.xpos - renderDistanceWidth * 32 / 2 + width / 2, (int) pos.ypos
				- renderDistanceHeight * 32 / 2 + height / 2, renderDistanceWidth * 32,
				renderDistanceHeight * 32);

		hudm.render(g);
		guim.render(g);

		playermm.render(g);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = true;
		}

		if (key == KeyEvent.VK_S) {
			down = true;
		}

		if (key == KeyEvent.VK_A) {
			left = true;
		}

		if (key == KeyEvent.VK_D) {
			right = true;
		}

		if (key == KeyEvent.VK_SHIFT) {
			running = true;
		}

		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = false;
		}

		if (key == KeyEvent.VK_S) {
			down = false;
		}

		if (key == KeyEvent.VK_A) {
			left = false;
		}

		if (key == KeyEvent.VK_D) {
			right = false;
		}

		if (key == KeyEvent.VK_SHIFT) {
			running = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// ///////////////////////////////
// Getters
	// //////////////////////////////

	public static VectorToF getPos() {
		return pos;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getSlowdown() {
		return slowdown;
	}

}
