const LEAF_PATH =
  'M0,0 C-14,-9 -19,-29 -8,-49 C-4,-57 0,-62 0,-62 C0,-62 4,-57 8,-49 C19,-29 14,-9 0,0 Z'

const LEAVES = [
  { angle: -50, threshold: 0.04, scale: 0.85, color: '#5c6e4c' },
  { angle: 50, threshold: 0.14, scale: 0.8, color: '#5c6e4c' },
  { angle: -25, threshold: 0.32, scale: 1.0, color: '#71835c' },
  { angle: 25, threshold: 0.48, scale: 0.95, color: '#657a52' },
  { angle: -6, threshold: 0.68, scale: 1.1, color: '#7d9166' },
  { angle: 10, threshold: 0.84, scale: 1.05, color: '#4f5f42' },
]

const REVEAL_SPAN = 0.18

function leafProgress(t, threshold) {
  return Math.max(0, Math.min(1, (t - threshold) / REVEAL_SPAN))
}

export function PlantIllustration({ growthPoints, size = 48 }) {
  const t = Math.max(0, Math.min(1, growthPoints / 100))

  return (
    <svg viewBox="0 0 120 120" width={size} height={size} className="shrink-0 overflow-visible">
      <ellipse cx="60" cy="99" rx="18" ry="3.5" fill="#33261c" opacity="0.15" />

      <path d="M46,86 L74,86 L71,102 Q60,105.5 49,102 Z" fill="#c98a5e" />
      <path d="M45,86 L75,86 L74,90.5 L46,90.5 Z" fill="#dba274" />
      <ellipse cx="60" cy="86" rx="14.5" ry="3" fill="#3c2e23" />

      <g style={{ animation: 'sway 4.5s ease-in-out infinite', transformOrigin: '60px 86px' }}>
        <g transform="translate(60,86)">
          {LEAVES.map((leaf, i) => {
            const p = leafProgress(t, leaf.threshold)
            const s = leaf.scale * (0.4 + p * 0.6)
            return (
              <path
                key={i}
                d={LEAF_PATH}
                fill={leaf.color}
                style={{
                  transform: `rotate(${leaf.angle}deg) scale(${s})`,
                  opacity: p,
                  transformOrigin: '0px 0px',
                  transition: 'transform 700ms ease-out, opacity 700ms ease-out',
                }}
              />
            )
          })}
        </g>
      </g>
    </svg>
  )
}
