(def filter (variable 0.5))
(bind-slider "/1/fader5" filter)

(def hfilter (variable 0.5))
(def lfilter (variable 0.5))

(bind-slider "/1/fader4" hfilter)
(bind-slider "/1/fader3" lfilter)

(play (HPF (LPF (mult (load-clip "/home/john/Desktop/partyrock.wav") (mult filter 10.0)) lfilter) hfilter))

(def beats (map load-clip (list-files "/home/john/Desktop/Beats/drum3/tape clean")))

(def beats (map load-clip (list-files "/home/john/Desktop/Beats/drums3/driven kit/")))


(map println (list-files "/home/john/Desktop/Beats/drums3/driven kit/"))


(def m (metronome 480))

(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
(for [x (range 1 17)] (fn [] 
            (do-at (m (+ (m) 1)) 
                   #(play (nth beats x)))))))


(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
beats))


(def thx (load-clip "/home/john/Desktop/thx.wav"))

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