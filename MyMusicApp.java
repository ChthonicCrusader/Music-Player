import org.jfugue.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.String;
//import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;


class Ball
{
	int index;
	int red,green,blue;
	String text;
	int xpos,ypos;

	public void Ball()
	{
	}

	public void setValues(int i,int r,int g,int b, String t,int x,int y)
	{
		index=i;
		red=r;green=g;blue=b;
		text=t;
		xpos=x;ypos=y;
	}

	public void changeColor(int r,int g,int b)
	{
		red=r;
		green=g;
		blue=b;
	}

	public void changeText(String s)
	{
		text=s;
	}

}


class Painter extends JPanel
{
	int n;
	int p;
	Ball[] b;
	int menu;

	public void setBalls(int num,int played,String[] notes)
	{
		n=num;
		p=played;
		b=new Ball[n];
		int x=70,y=70;
		for(int i=0;i<n;i++){
			b[i]=new Ball();
			b[i].setValues(i,0,0,1,notes[i],x,y);
			x+=200;
		}
	}

	public void changeState(int pnew)
	{
		p=pnew;
		b[p].changeColor(1,0,0);
	}

	public String ithNote(int i)
	{
		if(i>=n)
		return "?";

		return b[i].text;
	}


	public void drawBall(Ball b, Graphics g)
	{
		Color ballcolor=new Color((int)b.red*255,(int)b.green*255,(int)b.blue*255);
		g.setColor(ballcolor);
		g.fillOval(b.xpos,b.ypos,100,100);
		g.setColor(Color.white);
		Font font = new Font("Serif", Font.PLAIN, 48);
        g.setFont(font);
        g.drawString(b.text, b.xpos+35, b.ypos+60); 
	}

	public void paintComponent(Graphics g)
	{
		Font font = new Font("Serif", Font.PLAIN, 48);
		switch(menu)
		{
		case 0: 
        	g.setFont(font);
        	g.drawString("MUSIC TUTOR", 100, 100);
        	g.drawString("1. Practice", 100, 250);
        	g.drawString("2. Learn songs", 100, 400);
        	g.drawString("3. Instructions", 100, 550);
        	g.drawString("4. Exit", 100, 700);
        	break;
        case 1:
        	g.setColor(Color.black);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
        	g.setFont(font);
        	g.setColor(Color.white);
        	g.drawString("Practice Mode", 100, 100);
			break;
		case 2:
			g.setColor(Color.black);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			g.setColor(Color.blue);
			g.fillOval(70,70,100,100);	
			for(int i=0;i<n;i++)
			{
				drawBall(b[i],g);
			}
			break;
		case 3:
			g.setColor(Color.black);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
        	g.setFont(font);
        	g.setColor(Color.white);
        	g.drawString("Instructions", 100, 100);
			break;
		case 4:
			System.exit(0);
			break;
		}

	}
}

class MyMusicApp extends JFrame implements KeyListener
{

	Painter painter;
	JFrame frame;
	int n,p;
	String[] notes;
	int downkey; //1 when pressed, 0 else
	int upkey; //1 when pressed, 0 else
	//org.jfugue.Player player;

	public MyMusicApp()
	{
      	frame=new JFrame();
      	frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		notes=new String[4];
		notes[0]="A#";
		notes[1]="B4";
		notes[2]="C6";
		notes[3]="D#4";
		n=4;p=0;

		painter=new Painter();
		painter.menu=0;
		painter.setBalls(n,p,notes);

		frame.setSize(1500,870);
		frame.getContentPane().add(painter);
		frame.setVisible(true);
		
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		switch(painter.menu)
		{
			case 0: 
					if(e.getKeyChar()=='1')
					{
						painter.menu=1;
						frame.repaint();
					}
					else if(e.getKeyChar()=='2')
					{
						painter.menu=2;
						frame.repaint();
					}
					else if(e.getKeyChar()=='3')
					{
						painter.menu=3;
						frame.repaint();
					}
					else if(e.getKeyChar()=='4')
					{
						painter.menu=4;
						frame.repaint();
					}
					break;
			case 1:	
					//org.jfugue.Player player;
					if (e.getKeyCode()==KeyEvent.VK_UP)
					{
						upkey=1;
						//System.out.println("octave up");
					}
					else if (e.getKeyCode()==KeyEvent.VK_DOWN)
					{
						downkey=1;
						//System.out.println("octave down");
					} 
					else if (e.getKeyChar()!='q')
					{
						System.out.println(e.getKeyChar());
						org.jfugue.Player player=new org.jfugue.Player();
						if (e.getKeyChar() >='a') //is lower case
						{
							String s=new String ( new char[] {Character.toUpperCase(e.getKeyChar())});
							if (upkey==1)
							{
								s=s.concat("6");
							}
							else if (downkey==1)
							{
								s=s.concat("4");
							}
							System.out.println(s);	
							player.play(s);
						}
						else // if upper case
						{
							String s=new String ( new char[] {Character.toUpperCase(e.getKeyChar())});
							s=s.concat("#");
							if (upkey==1)
							{
								s=s.concat("6");
							}
							else if (downkey==1)
							{
								s=s.concat("4");
							}
							System.out.println(s);						
							player.play(s);
						}
					}
					else if(e.getKeyChar()=='q')
					{
						painter.menu=0;
						frame.repaint();
					}
					break;	
			case 2:	String notetoplay=painter.ithNote(p);
					char a=e.getKeyChar();
					//getting the note played
					String note=new String ( new char[] {Character.toUpperCase(a)});
					if (e.getKeyCode()==KeyEvent.VK_UP)
					{
						upkey=1;
						return;
						//System.out.println("octave up");
					}
					else if (e.getKeyCode()==KeyEvent.VK_DOWN)
					{
						downkey=1;
						return;
						//System.out.println("octave down");
					} 
					if (a>='a') //lowercase
					{
						
					}
					else//deciding if its sharp
					{
						note=note.concat("#");
					}
					//deciding the octace
					if (upkey==1)
					{
						note=note.concat("6");
					}
					else if(downkey==1)
					{
						note=note.concat("4");
					}
					System.out.print(note);
					System.out.print(" ");
					System.out.println(notetoplay);
					//checking the note with the next note
					if(a=='q')
					{
						painter.menu=0;
						frame.repaint();
					}
					else if(note.equals(notetoplay))
					{
						System.out.println(note);
						org.jfugue.Player player=new org.jfugue.Player();
						player.play(note);
						painter.changeState(p);
						p++;
						frame.repaint();
					}								
					break;
			case 3:
					if(e.getKeyChar()=='q')
					{
						painter.menu=0;
						frame.repaint();
					}	
					break;
			
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_UP)
		{
			upkey=0;
			//System.out.println("octave normal");
		}
		else if (e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			downkey=0;
			//System.out.println("octave normal");
		} 
	}
	public static void main(String[] args)
	{
		org.jfugue.Player welcome=new org.jfugue.Player();
		welcome.play("A A A");
		new MyMusicApp();
/*
		try
		{
			File f=new File("DSC_0105.avi");
			final javax.media.Player p=Manager.createRealizedPlayer(f.toURI().toURL());
  			p.start();
  		}
  		catch(Exception e)
  		{
  			System.out.println("Error in playing song from file");
  		}*/
	}
}

/*
		Player player = new Player();
		String s=new String();
		Pattern pattern = new Pattern(s);
		int c;
		char character;
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			do 
			{
				character = (char) br.read();
				System.out.print("you entered ");
				System.out.println(character);
				
				s = new String ( new char[] {character});
				pattern = new Pattern(s);
				player.play(pattern);

				br = new BufferedReader( new InputStreamReader(System.in));
				player=new Player();
			}while (character!='q');
		}
		catch(Exception e)
		{
			System.out.println("Error in input");
		}
*/

//javac -classpath .:jfugue-4.0.3.jar MyMusicApp.java
//java -classpath .:jfugue-4.0.3.jar MyMusicApp
