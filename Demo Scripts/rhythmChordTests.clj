;;Rhythm Demo. Not intended for full evaluation.

(def m (metronome 120))

(defn inst [freq] (mult (clip (string-inst freq 0.01 0.995) 0.1) 4.0))
(defn inst [freq] (string-inst freq 0.05 ))

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
(if (zero? (mod time 4)) (hat time))
(comment (if (zero? (mod time 8)) (drumThing)))
(play (nth notes (mod (* 1 time) (count notes))))))

(defn hatMelody [time]
(let [notes (gen-notes "G3 G3 G3 G3 G3 D3 D3 D3 D3 A3 A3 C3 C3 C3 C3 E3 B3 A3 D3")]
(nth (nth notes (mod time (count notes))) 0)))

(defn hat [time] 
(play (mult (LPF (HPF (add (triangle (hatMelody time)))) (variable (expenv 1.5))) 5.1)))

(defn drumThing []
(play (mult (LPF (add (mult (white-noise) 0.1) (LPF (saw 60) 0.01)) (variable (expenv 0.5))) 2.0)))

(drumThing)


(bind-touch (zipmap (gen-binds "/2/push!1" (range 1 17)) drums))

(def drums (map load-clip (list-files "/home/john/Desktop/Beats/drums606/warm kit")))


(defn rhythm [time]
(melody time)
(do-at (m (+ (m) 1))#(do (rhythm (inc time)))))

(fkill)
(kill-tasks)
(rhythm 0)