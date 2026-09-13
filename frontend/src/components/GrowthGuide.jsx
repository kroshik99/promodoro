import { PlantIllustration } from './PlantIllustration.jsx'

const STAGES = [
  { points: 5, label: 'seed' },
  { points: 30, label: 'sprout' },
  { points: 70, label: 'budding' },
  { points: 100, label: 'bloomed' },
]

export function GrowthGuide() {
  return (
    <section className="mt-5 rounded-xl border border-gray-200 bg-white p-5">
      <h2 className="mb-1 text-xs font-semibold uppercase tracking-wider text-gray-500">
        How growth works
      </h2>
      <p className="mb-4 text-[13px] text-gray-600">
        Every focused minute earns 1 growth point. Reach 100 and a plant blooms
        — your next completed session starts a new one.
      </p>
      <div className="grid grid-cols-2 gap-3 sm:grid-cols-4">
        {STAGES.map((stage) => (
          <div
            key={stage.label}
            className="flex flex-col items-center gap-1.5 rounded-lg border border-gray-200 bg-gray-50 py-3"
          >
            <PlantIllustration growthPoints={stage.points} size={40} />
            <span className="text-[12px] font-semibold capitalize">{stage.label}</span>
            <span className="text-[11px] text-gray-500">{stage.points} pts</span>
          </div>
        ))}
      </div>
    </section>
  )
}
