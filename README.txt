I completed this homework independently.

GitHub repo: https://github.com/GanlinO/591HW6.git

My class design is inspired by the "non-evil" version of the game.
The class EvilHangmanRunner is very similar to the provided class HangmanRunner, and its only function is to start the whole program by creating a new EvilHangman object and calling the start function.
The class EvilHangman is very similar to the provided class Hangman, and it mainly handles I/O, keeps track of each guess a user makes, and provides the wordlist and guesses to a DynamicSolution object.
The class DynamicSolution is very similar to the provided class Solution, and it creates a new word family every time a new guess is received by an EvilHangman object, determines if a partial solution needs to be updated, and print messages reporting progress or victory.
