(doseq 
[bind 
(zipmap 
(gen-binds "/1/push!1" (range 0 10))
(map string-inst (range 220 440 20))
)]
(bind-toggle (key bind) (var bind)))
(def hatPitch (variable 0))
(def hatVolume (variable 0))
(bind-slider "/1/fader1" hatPitch)
(bind-slider "/1/fader2" hatVolume)
(defn hat [] 
(LPF(white-noise)(variable (expenv 0.5))))
(bind-touch "/1/push1" (mult (hat (mult hatPitch 1000.0)) (mult hatVolume 10.0)))


(def dialevents (gen-binds "/3/rotary!1" (range 1 7)))
(def dialvars (map variable [0 0 0 0 0 0]))
(doseq [x (range 0 6)] (bind-slider (nth dialevents x) (nth dialvars x)))

(def drums (map #(mult (buffer % 0.75) 10.0) (map load-clip (list-files "/home/john/Desktop/drums"))))
(do drums)
(def binds (gen-binds "/1/push!1" (range 1 13)))
(for [x (range 0 12)] (bind-touch (nth binds x) (nth drums (+ x 5))))


(defn inst [freq]
(triangle (add (variable freq) (mult (sine (mult hatPitch 30.0)) (mult hatVolume 40.0))))
)

(def clipHeight (variable 1))
(bind-slider "/3/fader3" clipHeight)
(bind-toggle 
(zipmap 
(gen-binds "/1/push!1" (range 1 13)) 
(map saw (gen-scale "pentatonic major" "C2" 12))))