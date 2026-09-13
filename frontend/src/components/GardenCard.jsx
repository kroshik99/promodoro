import { PlantIllustration } from './PlantIllustration.jsx'

const STAGE_TARGET_POINTS = 100

export function GardenCard({ garden }) {
  const plants = garden?.plants ?? []

  return (
    <section className="rounded-xl border border-gray-200 bg-white p-5">
      <h2 className="mb-4 text-xs font-semibold uppercase tracking-wider text-gray-500">
        Garden
      </h2>

      <div className="mb-4.5 grid grid-cols-3 gap-2.5">
        <StatBox label="Growth" value={garden?.totalGrowthPoints ?? 0} />
        <StatBox label="Streak" value={garden?.currentStreak ?? 0} />
        <StatBox label="Best" value={garden?.longestStreak ?? 0} />
      </div>

      <div className="flex max-h-80 flex-col gap-2.5 overflow-y-auto">
        {plants.length === 0 ? (
          <div className="py-6 text-center text-[13px] text-gray-500">
            No plants yet. Complete a session to plant one.
          </div>
        ) : (
          plants.map((plant) => {
            const pct = Math.min(100, Math.round((plant.growthPoints / STAGE_TARGET_POINTS) * 100))
            return (
              <div
                key={plant.id}
                className="flex items-center gap-3 rounded-lg border border-gray-200 bg-gray-50 px-3 py-2.5"
                style={{ animation: 'fadeInUp 400ms ease-out' }}
              >
                <div className="flex h-12 w-12 shrink-0 items-end justify-center rounded-lg bg-gray-100">
                  <PlantIllustration growthPoints={plant.growthPoints} size={44} />
                </div>
                <div className="min-w-0 flex-1">
                  <div className="flex justify-between text-[13px] font-semibold">
                    <span>{plant.name}</span>
                    <span className="capitalize text-gray-500">{plant.stage}</span>
                  </div>
                  <div className="mt-1.5 h-1.5 overflow-hidden rounded-full bg-gray-200">
                    <div
                      className="h-full rounded-full bg-gray-700 transition-[width] duration-700 ease-out"
                      style={{ width: `${pct}%` }}
                    />
                  </div>
                </div>
              </div>
            )
          })
        )}
      </div>
    </section>
  )
}

function StatBox({ label, value }) {
  return (
    <div className="rounded-lg border border-gray-200 bg-gray-50 px-2.5 py-3 text-center">
      <div className="text-[22px] font-bold text-gray-900">{value}</div>
      <div className="mt-0.5 text-[11px] uppercase tracking-wider text-gray-500">{label}</div>
    </div>
  )
}
