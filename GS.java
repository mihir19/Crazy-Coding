
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihirkavatkar
 */
public class GS {
    public void reverse(String m[],String newm[],String manpref[][])
    {
        int j=0,i,a=0,b=0;
            for(i = m.length-1; i>=0;i--)
            {          
            newm[j] = m[i];
            //System.out.println(newm[j]);
            j++;
            }
            
           for (i = 0; i < manpref.length/2; i++)
           {
                for (j = 0; j < manpref[i].length; j++) 
                {
                String tmp = manpref[i][j];
                manpref[i][j] = manpref[manpref.length - i -1][j];
                manpref[manpref.length - i -1][j] = tmp;
                }
           }
    }
    public int getmanpos(String man[],String str,int N)
    {
        int i;
        for(i=0;i<N;i++)
               if(man[i].equals(str))
                   return i;
        return -1;
    }
    public int getwomanpos(String woman[],String str,int N)
    { int i;
        for(i=0;i<N;i++)
                     if(woman[i].equals(str))
                         return i;
                     return -1;
                 
    }
    public boolean nextpref(String womanpref[][],String currentP,String newP,int pos,int N)
    {
        int i;
        for(i=0;i<N;i++)
        {
            if(womanpref[pos][i].equals(newP))
            {
                return true;
            }
            if(womanpref[pos][i].equals(currentP))
            {
                return false;
            }
        }
        return false;
    }
    public void match(String m[],String w[],String manpref[][],String womanpref[][])
    {
        int engagecount = 0,i=0,j=0,f;
        int CN = m.length;
        int N = m.length;
        String man[] = m;
        String woman[] = w;
        String manpreflist[][] = manpref;
        String womanpreflist[][] = womanpref;
        boolean manEngagelist[] = new boolean[CN];
        String womanEngagelist[] = new String[CN];
        boolean first = false;
         while(engagecount<N)
         {
             for(f=0;f<N;f++)
             {
                 if(manEngagelist[f]==false)
                 {   break;}
             }
             
             for(j=0;j<N && manEngagelist[f]==false;j++)
             {
                 int pos=getwomanpos(woman,manpreflist[f][j],N);
                 
                 if(womanEngagelist[pos]==null)
                 {
                     womanEngagelist[pos] = man[f];
                     manEngagelist[f] = true;
                     engagecount++;
                     
                 }
                 else
                 {
                     String currentP = womanEngagelist[pos];
                     if(nextpref(womanpreflist,currentP,man[f],pos,N))
                     {
                         int nP=0;
                         womanEngagelist[pos]=man[f];
                         manEngagelist[f] = true;
                         
                         manEngagelist[getmanpos(man,currentP,N)] = false;
                     }
                 }
             }
             
         }
         System.out.println("   ");
         System.out.println("Output:");
         System.out.println("Engaged Couples:");
         System.out.println("   ");
         for(i=0;i<N;i++)
         {
             System.out.println(womanEngagelist[i]+"  "+woman[i]);
         }
    }
    public void woman_to_man(String nm[], String nw[],String nmanpref[][], String nwomanpref[][])
    {
        GS g = new GS();
        String w[] = nm;
        String m[] = nw;
        String manpref[][] = nwomanpref;
        String womanpref[][] = nmanpref;
        g.match(m,w,manpref,womanpref);
    }
    public static void main(String args[])
    {
        GS g = new GS();
        int r;
        Scanner sc = new Scanner(System.in);
        System.out.println("Gale-Shapley Algorithm");
        
        String m[] = {"Victor", "Wyatt", "Xavier", "Yancey", "Zeus"};
        String newm[];
        newm = new String[5];
        String new_manpref[][] = new String[5][5];
        String w[] = {"Amy", "Bertha", "Clare", "Diane", "Erika"};
        
        String manpref[][] = {{"Bertha", "Amy", "Diane", "Erika", "Clare"},
                              {"Diane", "Bertha", "Amy", "Clare", "Erika"},
                              {"Bertha", "Erika", "Clare", "Diane", "Amy"},
                              {"Amy", "Diane", "Clare", "Bertha", "Erika"},
                              {"Bertha", "Diane", "Amy", "Erika", "Clare"}
                            };
        
        String womanpref[][] = {{"Zeus", "Victor", "Wyatt", "Yancey", "Xavier"},
                                {"Xavier", "Wyatt", "Yancey", "Victor", "Zeus"},
                                {"Wyatt", "Xavier", "Yancey", "Zeus", "Victor"},
                                {"Victor", "Zeus", "Yancey", "Xavier", "Wyatt"},
                                {"Yancey", "Wyatt", "Zeus", "Xavier", "Victor"}
                             };
        
        System.out.println("Execution Options:");
        System.out.println("Press 1: Start from Victor to Zeus");
        System.out.println("Press 2: Start from Zeus to Victor");
        System.out.println("Press 3: Woman proposing mens: Amy to Erika");
        System.out.println("Press 4: Execute All the above options together using Threads Efficiently!!!");
        r = sc.nextInt();
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                 g.reverse(m,newm,manpref);
                g.match(newm,w,manpref,womanpref);
            }
        });
         Thread thread1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                 g.woman_to_man(m, w, manpref, womanpref);
            }
        });
        switch(r)
                {
                    case 1: 
                         g.match(m,w,manpref,womanpref);
                        break;
                    case 2:
                        g.reverse(m,newm,manpref);
                        g.match(newm,w,manpref,womanpref);
                        break;
                    case 3:
                        g.woman_to_man(m, w, manpref, womanpref);
                        break;
                    case 4:
                        System.out.println("Victor to Zeus");
                        g.match(m,w,manpref,womanpref);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("     ");
                        System.out.println("Zeus to Victor");
                        thread.start(); 
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("     ");
                        System.out.println("Amy to Erika");
                        thread1.start();
                        break;
                    default:
                        System.out.println("Wrong Choice");
                        break;
                     }      
    }
}
