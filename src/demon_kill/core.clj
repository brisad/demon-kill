(ns demon-kill.core
  (:gen-class)
  (:require clojure.set))

(def secret-word
  (rand-nth
   ["apoplectic" "conflagration" "impervious" "juxtaposition" "maelstrom"
    "obduracy" "petulance" "progressive" "rehabilitation" "quagmire"
    "sanctimonious" "tantamount" "vaccination" "verisimilitude"
    "vicissitude"]))

(def world
  [" @ <- you              demon -> &"
   " @  the demon is coming! &"
   " @               &"
   " @       &"
   " @ &"
   " @&"
   " @& The demon hits! The demon hits! You die..."])

(defn draw-world
  "Draw player and demon."
  [{step :step}]
  (println (get world step))
  (println))

(defn draw-word
  "Draw the obscured word."
  [{correct-guesses :correct-guesses}]
  (doseq [char secret-word]
    (if (correct-guesses char)
      (print char)
      (print "_")))
  (println))

(defn draw
  "Draw current game state."
  [state]
  (draw-world state)
  (draw-word state))

(defn dead?
  [{step :step}]
  (>= (inc step) (count world)))

(defn guessed-all?
  "Return true if all correct characters have been found."
  [{correct-guesses :correct-guesses}]
  (empty? (clojure.set/difference (set secret-word) correct-guesses)))

(defn read-char
  "Read one character from *in*. Consumes whole line."
  []
  (first (read-line)))

(defn correct-guess?
  "Return true if guess was correct."
  [guess {correct-guesses :correct-guesses}]
  (and (contains? (set secret-word) guess)
       (not (contains? correct-guesses guess))))

(defn next-guess
  "Prompt user for next character."
  []
  (print "Enter your guess: ")
  (flush)
  (read-char))

(defn -main
  "Starts the game demon-kill"
  [& args]
  (println "Welcome to demon-kill!")
  (loop [state {:step 0, :correct-guesses #{}}]
    (draw state)
    (cond
     (guessed-all? state) (println "You win!")

     (dead? state)
     (do
       (println "You lose!")
       (println "The correct word was:" secret-word))

     :else
     (let [guess (next-guess)]
       (if (correct-guess? guess state)
         (recur (update-in state [:correct-guesses] conj guess))
         (recur (update-in state [:step] inc)))))))

