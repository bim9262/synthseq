(defn inst [freq] (mult (clip (string-inst freq 0.01 0.99) 0.1) 4.0))
(defn inst [freq] (LPF (add (mult (HPF (white-noise)) 0.2) (saw freq)) (variable (expenv 1.5))))
(comment (defn inst [freq] (LPF (saw freq) (variable (expenv 1.5)))))
(def rhythm-time 0.0)

(comment (def melodyString "A3 C5 D E5m F5M A G CM7 A C3"))
(comment (def melodyString "A3 CM D A3mM7 F"))
(comment (def melodyString "G4M G4M7 G4M G4M7 G4M D4sus4 D4M D4sus4 D4M D4M Am Am C4M C4M C4M C4M Em Bm Am DM"))
(comment (def melodyString "E5 D CM7 Dm EM E E"))
(def melodyString "A3 C5 D Em F5M A G CM7 A C3")
(comment (def melodyString (reduce #(str % " " %2) (reverse (.split melodyString " ")))))


(defn melody [time]
(let [notes (chord-inst inst 
melodyString)]
(if (zero? (mod time 2)) (hat time))
(if (zero? (mod time 4)) (drumThing))
(play (nth notes (mod time (count notes))))))

(defn hatMelody [time]
(let [notes (gen-notes "A3 A3 B3 B3 A2 A2 B2 B2 E2 C3 D4 D4 D4")]
(nth (nth notes (mod time (count notes))) 0)))

(defn hat [time] 
(play (mult (LPF (HPF (add (triangle (hatMelody time)))) (variable (expenv 0.5))) 10.1)))

(defn drumThing []
(play (mult (LPF (white-noise) (variable (expenv 1.0))) 2.0)))

(drumThing)

(hatMelody 4)
(def rate 400)

(defn rhythm [time]
(melody time)
(apply-at rate #(rhythm (inc time))))

(kill-tasks)
(rhythm 0)
(Thread/sleep 5)
(rhythm 0)
(Thread/sleep 10)
(rhythm 0)