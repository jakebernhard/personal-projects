import requests
from random import choice
from termcolor import colored
    
def create_word_list(url): 
    res = requests.get(url)
    word_list = res.text.split("\n")
    return word_list
    


def start_game():
    print("Welcome to Wordle!")
    list_of_words = create_word_list('https://gist.githubusercontent.com/cfreshman/dec102adb5e60a8299857cbf78f6cf57/raw/15ec4eb961a969d6e263cea4d5b4a180bdeee7bd/answers.txt')
    valid_guesses = create_word_list('https://gist.githubusercontent.com/cfreshman/dec102adb5e60a8299857cbf78f6cf57/raw/15ec4eb961a969d6e263cea4d5b4a180bdeee7bd/allowed.txt')
    answer = choice(list_of_words)
    num_of_guesses = 0
    guess = ""
    while guess != answer and num_of_guesses < 6:
        guess = input("Please enter a word: ")
        while guess not in valid_guesses and guess not in list_of_words:
            guess = input("Please enter a valid word: ")
        check_word(guess, answer)
        num_of_guesses += 1
    if guess == answer:
        print(f"Congratulation! You win, the word was {answer}!")
        print(f"You got it in {num_of_guesses} guesses!")
    else:
        print(f"Sorry you lost, the word was {answer}. ")


def check_word(guess, answer):
    answer_list = [make_word_dict(char) for char in guess]
    result = []
    for i in range(len(guess)):
        if guess[i] in answer:
            answer_list[i][guess[i]] = "on_yellow"
            if answer.count(guess[i]) < answer_list.count(
                    {guess[i]: "on_yellow"}):
                answer_list[i][guess[i]] = "on_dark_grey"
        else:
            answer_list[i][guess[i]] = "on_dark_grey"
    for i in range(len(guess)):
        if guess[i] == answer[i]:
            answer_list[i][guess[i]] = "on_green"
            if answer.count(guess[i]) < guess.count(guess[i]):
                for val in answer_list:
                    if guess[i] in val and val[guess[i]] != "on_green" and answer.count(guess[i]) < answer_list.count(
                            {guess[i]: "on_yellow"}) + answer_list.count({guess[i]: "on_green"}):
                        val[guess[i]] = "on_dark_grey"
    for answer in answer_list:
        for key in answer.keys():
            result.append(colored(f" {key.upper()} ", "white", answer[key]))
    print(" ".join(result))


def make_word_dict(l):
    return {l: None}


start_game()
