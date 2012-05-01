;;get wave files
(def files (list-files "/home/john/Beats"))

;;load wave files
(def clips (map load-clip files))
(do clips)

;;play selected clips
(play (add (map #(nth clips %) [])))

;;Shifting LPF on clips
(play (LPF (add (map #(nth clips %) []))(add (variable 0.5) (mult (sine 2) 0.4))))

(defn drum [freq]
(let
[sin (clip (mult (mult (sine freq) (linenv 0.7)) 2.0) 1.5)
noise (mult (LPF (mult (white-noise) (linenv 0.2)) 0.05) 0.5)
res (add sin noise)]
(mult res 3.0)))
(play (drum 120))

(defn hat [] 
(let
[noise (white-noise)
sqr (mult (mult (pulse 700.0 0.2) (expenv 0.2)) 0.3)
res (mult (add sqr noise) (expenv 0.2))]
(play (mult (LPF res 0.9) 4.0))))
(hat)

(defn snare []
(let 
[noise (white-noise)
env (expenv 0.5)]

(play (LPF noise (variable env)))))

(defn rhythm [t]
(let [beat [2 1 3 1 2 1 3 1 2 1 3 1 2 1 3 3 1 2 2 1]]
(cond (= 1 (nth beat (mod t (count beat)))) (hat)
          (= 2 (nth beat (mod t (count beat)))) (snare)
 (= 3 (nth beat (mod t (count beat)))) (play (drum 80)))
(do-at (+ (now) 400) #(rhythm (inc t))))
)
(kill-tasks)
(rhythm 0)