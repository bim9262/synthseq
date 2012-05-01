"pop-punk chord progression: I V vi IV"

(bind-toggle
(zipmap
(gen-binds "/2/push!1" (range 1 17))
(chord-inst string-inst "CM G3M F3M A3m D3M D3M7 C3M C3M7 G3M G3M7 F3M7 F3M7 E3M B2M Cs3m A2M")))

(bind-toggle
(zipmap
(gen-binds "/2/push!1" (range 1 17))
(chord-inst saw "Cm C5M Fm GM AM DM")))

(bind-toggle
(zipmap
(gen-binds "/2/push!1" (range 1 17))
(chord-inst triangle 
"E2M B2M Cs2m A2M
 E3M B3M Cs3m A3M
 E4M B4M Cs4m A4M
 A3M EM  Fs3m DM")))

(bind-toggle
(zipmap
(gen-binds "/1/push!1" (range 1 17))
(map string-inst (gen-scale "chromatic" "E4" 16))))