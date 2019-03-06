#include <stdio.h>
#include <string.h>
#include <stdlib.h>


#define MAX 99
void squeeze(char *str, char c);
void printBackwards(char str[]);
int main() {
    char c;
    char *str = malloc(MAX);
    printf("Enter a character:");
    scanf("%c", &c);
    getchar();
    printf("Enter a string less than 99 characters:");
    fgets(str, MAX, stdin);
    squeeze(str, c);
    str[strlen(str) - 1] = ':';
    printf("FWD:%s\n", str);
    printBackwards(str);

}

void printBackwards(char str[]){
    char back[MAX];
    int j = 0;
    for(int i = strlen(str) - 1; i >= -1; i--){
        back[j] = str[i];
        j++;
    }
    back[strlen(back)] = ':';
    printf("BWD%s\n", back);
}


void squeeze(char *str, char c){
    for(int i = 0; i < strlen(str); i++){
        if(str[i] == c){
            memmove(&str[i], &str[i+1], strlen(str) - i);
            i--;
        }
    }
}
