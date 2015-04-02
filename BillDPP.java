/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DPP;
import java.util.*;
/**
 *
 * @author mihirkavatkar
 */
public class BillDPP {
    
    int max(int a,int b)
    {
        return a>b? a: b;
    }
    int index(int a,int N,int ac,int M[]){
        int i;
        for(i=1;i<=N;i++)
        {
            if(M[i]==a)
            {
                ac = i;
            }
        }
        return ac;
    }
    public static void main(String args[])
    {
        BillDPP b = new BillDPP();
        int N,i,j,L,a=0,bc=0,ac=0,ind=0;
     
        String choices = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length L");
        L = sc.nextInt();
        System.out.println("Enter the Number of Possible sites for billboards");
        N = sc.nextInt();
       String result[] = new String[N+1];
        int sites[] = new int[N+1];
        String finals[] = new String[N+1];
        int revenue[] = new int[N+1];
        int M[] = new int[N+1];
        int P[] = new int[N+1];
        for(i=1;i<=N;i++)
        {
                
            System.out.println("Enter the Miles for the billboard"+(i));
            sites[i] = sc.nextInt();
            System.out.println("Enter the Revenue for the billboard"+(i));
            revenue[i] = sc.nextInt();
            finals[i] = " ";
           
        }
        if(sites[N]>L){
            System.out.println("Length Exceeding the limit");
            exit(0);
        }
        for(i=1;i<=N;i++)
        {
            for(j=i+1;j<=N;j++)
            {
                if(sites[j]-sites[i]>5)
                    P[j]=i;  
            }
        }
        //P[0] = -1;
       // System.out.print(P.length);
        
        for(i=1;i<=N;i++)
            System.out.println(P[i]);
        System.out.println(" ");
        M[0]=0;
        for(j=1;j<=N;j++)
        {
           
           
                M[j] = b.max(revenue[j]+ M[P[j]],M[j-1]); 
                 if((M[P[j]]+revenue[j])>M[j-1])
            {
                ind = b.index(M[P[j]],N,ac,M);
                finals[j] = finals[ind]+" "+j;
                
                System.out.println("\nChosen:"+finals[j]);
            }
            else{
                finals[j] = finals[j-1];
                System.out.println("\nChosen:"+finals[j]);
            }
                System.out.print("Revenue: "+M[j]);
                
            
        }
        System.out.print("\nMaximum Revenue:\n"+M[N]);
        System.out.print("\nChosen Points \n"+finals[N]);
            
    }
}
