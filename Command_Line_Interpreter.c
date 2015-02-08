/*
Mihir Kavatkar

Program:

Following program is a Command Interpreter that executes unix commands using a system call and even few of the DOS commands.

Steps to compile and execute:

1.
syntax: gcc location/filename.c
Eg.
gcc  /Users/mihirkavatkar/Documents/Advance\ Computer\ System/Assignment\ Solution/Assignment_1.c

2.
 ~/a.out 

Note:
while executing commands maintain white spaces
example:
1. ls -l
2. ls -l | wc
3. ls > test.txt
*/


#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <errno.h>
#include <stdlib.h>

#define string_size 100

int parser(char *, char **, char **, int *);

void dos_run(char *input, char **array)
{
     char *cmdargv[string_size], *suppl = NULL;
     int flag=0;
     parser(input, cmdargv, &suppl, &flag);

     pid_t pid;
     int status;
     
pid = fork(); 
if (pid < 0) {
          printf("Child Process Failed");
          exit(0);
     }
     else if (pid == 0) {
          if (execvp(*cmdargv, cmdargv) < 0) {
               printf("Execution Failed");
               exit(0);
          }
     }
     else {
          while (wait(&status) != pid)
               ;
     }
}
void execute(char **cmdargv, int flag, char **supplptr)
{
     
     pid_t pid1,pid2;
     char *cmdargv1[string_size],*suppl1 = NULL;
     int pfds[2];
     int flag1 = 0,status;
     FILE *fp;
     if(flag == 4)
     {
          if(pipe(pfds))
          {
               fprintf(stderr, "Error: Pipe failure");
               exit(-1);
          }
          parser(*supplptr, cmdargv1, &suppl1, &flag1);
     }
     pid1 = fork();
     if(pid1 < 0)
     {
          printf("Error");
          exit(-1);
     }
     else if(pid1 == 0)
     {
               if(flag == 2)
               {
                    fp = fopen(*supplptr, "w+");
                    dup2(fileno(fp), 1);
               }
               if(flag == 5)
               {
                    fp = fopen(*supplptr, "a");
                    dup2(fileno(fp), 1);
               }
               if(flag == 3)
               {
                    fp = fopen(*supplptr, "r");
                    dup2(fileno(fp), 1);     
               }
               if(flag == 4)
               {
                    close(pfds[0]);
                    dup2(pfds[1], fileno(stdout));
                    close(pfds[1]);
               }
          execvp(*cmdargv, cmdargv);
     }
     else
     {
          if(flag == 1)
               ;
          else if(flag == 4)
          {
               waitpid(pid1, &status, 0);
               pid2 = fork();
               if(pid2 < 0)
               {
                    printf("Error");
                    exit(-1);
               }
               else if(pid2==0)
               {
                    close(pfds[1]);
                    dup2(pfds[0],fileno(stdin));
                    close(pfds[0]);
                    execvp(*cmdargv1,cmdargv1);
               }
               else
               {;
                    close(pfds[0]);
                    close(pfds[1]);
               }
          }
          else
               waitpid(pid1,&status,0);
     }
}
void strip(char *stptr)
{
     while(*stptr != ' ' && *stptr != '\n' && *stptr != '\t')
          stptr++;

     *stptr = '\0';
}
int parser(char *input, char *cmdargv[], char **supplptr, int *flagptr)
{
     char *stptr = input;
     int cmdargs = 0,finish = 0;
     //printf("%s\n", input);
     while(*stptr != '\0' && finish == 0)
     {
          *cmdargv = stptr;
          cmdargs++;
          //printf("%d\n", *cmdargv);
          while(*stptr != ' ' && *stptr != '\n' && *stptr != '\0' && *stptr != '\t' && finish == 0)
          {
               switch(*stptr)
               {
                    case '&':
                         *flagptr = 1;
                    break;
                    case '>':
                         *flagptr = 2;
                         *cmdargv = NULL;
                         stptr++;

                         if(*stptr == '>')
                         {
                              *flagptr = 5;
                              stptr++;
                         }

                         while(*stptr == ' ' || *stptr == '\t')
                         {
                              stptr++;
                         }
                         *supplptr = stptr;
                         strip(*supplptr);
                         finish = 1;
                         break;
                    case '<':
                         *flagptr = 3;
                         *cmdargv = NULL;
                         stptr++;

                         while(*stptr == ' ' || *stptr == '\t')
                         {
                              stptr++;
                         }
                         *supplptr = stptr;
                         strip(*supplptr);
                         finish = 1;
                         break;
                    case '|':
                         *flagptr = 4;
                         *cmdargv = NULL;
                         stptr++;

                         while(*stptr == ' ' || *stptr == '\t')
                              stptr++;
                         *supplptr = stptr;
                         finish = 1;
                         break;
               }
               stptr++;
          }
          while((*stptr == ' ' || *stptr == '\n' || *stptr =='\t') && finish == 0)
          {
               *stptr = '\0';
               stptr++;
          }


          cmdargv++;
     }

          *cmdargv = NULL;
          //printf("%d\n", cmdargs);
          return cmdargs;
}
 int main(int argc, char *argv[])
{
     char *input,*dos_input,*cmdargv[string_size], *suppl = NULL;
     char *list[9][2]={ {"dir", "ls"}, {"del", "rm"}, {"copy", "cp"}, {"move", "mv"}, {"type", "cat"}, {"more < file", "more file"}, {"md", "mkdir"}, {"rd", "rmdir"}, {"cls", "clear"} };
     //char *list[9]= {"dir", "del", "copy", "move", "type", "more < file", "md", "rd", "cls"};
     char curDir[100],*array[80];
     int flag = 0,cmdargs,mode=0777;
     size_t len = string_size;
     
     input = (char*)malloc(sizeof(char)*string_size);

     while(1)
     {
          flag = 0;
          getcwd(curDir, 100);
          printf("%s@%s->", getlogin(),curDir);
          getline(&input, &len, stdin);
          //printf("%s\n", input);
          
          //printf("%d%s\n", result,list[result][1]);
          //printf("%s\n", dos_input);
          if(strcmp(input,"exit\n")==0)
               exit(0);
                    cmdargs = parser(input, cmdargv, &suppl, &flag);
          //printf("%s\n", *cmdargv);
               for(int i=0;i<9;i++)
               {
                    for(int j=0;j<2;j++)
                    {
                         if(strcmp(input,list[i][j])==0)
                         {
                              dos_input = list[i][j+1];
                              dos_run(dos_input,array);

                         }
                         else
                              break;
                    }
               }
               

               //printf("%s\n", *cmdargv);
          if(strcmp(*cmdargv,"cd")==0)
          {
               chdir(cmdargv[1]);
          }
          else
          {
               execute(cmdargv, flag, &suppl);
          }
     }
     return 0;
}