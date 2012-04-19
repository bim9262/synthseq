(defn inst [freq] (string-inst freq 0.21 0.998))

(def rhythm-time 0.0)

(comment "A3 C5 D E5m F5M A G CM7 A C3")
(comment (def melodyString "E D C D E E E"))
(def melodyString "A3 C5 D E5m F5M A G CM7 A C3")
(comment (def melodyString (reduce #(str % " " %2) (reverse (.split melodyString " ")))))


(defn melody [time]
(let [notes (chord-inst inst 
melodyString)]
(if (zero? (mod time 2)) (hat))
(play (nth notes (mod time (count notes))))))

(defn hat [] 
(play (mult (LPF (HPF (add (triangle 60)(mult (sine 880) 0.1))) (variable (expenv 0.5))) 2.1)))

(hat)


(defn rhythm [time]
(melody time)
(apply-at (* 250 (nth (range 1 5) (mod (* 7 9) 4))) #(rhythm (inc time))))

(kill-tasks)
(rhythm 0)