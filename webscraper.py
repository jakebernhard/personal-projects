import requests
from bs4 import BeautifulSoup
from random import choice


def extract_data(soup):
	data = soup.select(".quote")
	quotes = []
	for quote in data:
		information = []
		q = quote.find(class_="text")
		information.append(q.get_text())
		a = quote.find(class_="author")
		information.append(a.get_text())
		tag = quote.find("a")
		href = tag.get("href")
		information.append(href)
		quotes.append(information)
	return quotes


def get_all_data():
	base_url = "http://quotes.toscrape.com" 
	page = "/page/1/"
	url = base_url + page
	full_data = []
	while page:
		res = requests.get(url)
		soup = BeautifulSoup(res.text, "html.parser")
		data = extract_data(soup)
		full_data.extend(data)
		nxt = soup.find(class_="next")
		page = nxt.find("a")["href"] if nxt else None
		if page:
			url = base_url + page
		else:
			break
	return full_data



def get_hint_1(link):
	base_url = "http://quotes.toscrape.com"
	url = base_url + link
	res = requests.get(url)
	soup = BeautifulSoup(res.text, "html.parser")
	details = soup.find(class_="author-details")
	info = details.find("p").get_text()
	return f"The author was born on{info[5:]}"



def get_hint_3(name):
	names = name.split()
	last_name= names[-1]
	return f"Their last name starts with {last_name[0]}"



def play_game():
	data = get_all_data()
	answer = choice(data)
	quote = answer[0]
	name = answer[1]
	hint_1 = get_hint_1(answer[2])
	hint_2 = f"Their first name starts with {name[0]}"
	hint_3 = get_hint_3(name)
	hints = [hint_1, hint_2, hint_3]
	guesses_remaining = 4
	print("Heres a quote:\n")
	print(quote + "\n")
	while guesses_remaining > 0:
		guess = input(f"Who said this? Guesses remaining: {guesses_remaining}. ")
		guesses_remaining -= 1
		if guess == name:
			break
		else:
			if guesses_remaining > 0:
				print("Here's a hint: " + hints[3-guesses_remaining] + "\n")
	if guess == name:
		print("You got it right! Congratulations!")
	else:
		print(f"Sorry you lose, the answer was {name}!")


def full_game():
	game_status = "y"
	while game_status == "y":
		play_game()
		game_status = input("Would you like to play again? (y/n) ")
	print("Thank you for playing!")



full_game()



