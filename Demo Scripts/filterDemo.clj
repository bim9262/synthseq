(def filter (variable 0.5))
(bind-slider "/1/fader5" filter)

(def hfilter (variable 0.5))
(def lfilter (variable 0.5))

(bind-slider "/1/fader4" hfilter)
(bind-slider "/1/fader3" lfilter)

(play (HPF (LPF (mult (load-clip "Sample Sounds/partyrock.wav") (mult filter 10.0)) lfilter) hfilter))

(def beats (map load-clip (list-files "Sample Sounds/Beats/drum3/tape clean")))

(def beats (map load-clip (list-files "Sample Sounds/Beats/waves/")))


(map println (list-files "Sample Sounds/Beats/drums3/driven kit/"))


(def m (metronome 240))

(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
(for [x (range 1 17)] (fn [] 
            (do-at (m (+ (m) 1)) 
                   #(play (nth beats x)))))))

(def noise (saw (add (variable 10000) (mult (sine 2) (variable 10000)))))

(defn wub [freq] (LPF (saw freq) (variable (LPF (saw 10) 0.05))))

(bind-toggle
(zipmap
(gen-binds "/2/push!1" (range 1 17))
(map wub (gen-scale "minor" "C1" 16))))

(bind-toggle "/2/push1" noise)


(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
beats))


(def thx (load-clip "Sample Sounds/thx.wav"))

(def beat1 [1 1 9 1])
(def beat2 [1 1 0 9 1 1 2 1 1 0 1 9 1 1 2 1])
(def beat3 [1 1 1 2 1 1 2 15 1 1 1 0 1 1 7 16])

(play (nth beats 9))
(defn rhythm [t]
(play (nth beats (nth beat1 (mod t (count beat1)))))
(play (nth beats (nth beat2 (mod t (count beat2)))))
(play (nth beats (nth beat3 (mod t (count beat3)))))
(do-at (m (+ (m) 2)) #(rhythm (inc t))))

(kill-tasks)
(rhythm 0)

(bind-touch "/2/push16" (mult thx 7.0))