import heroPlant from '../assets/hero-plant.svg'

export function HeroBanner() {
  return (
    <section className="mb-7 flex flex-col items-center gap-6 overflow-hidden rounded-2xl border border-[#e6ddc9] bg-[#fbf6ec] p-6 sm:flex-row sm:gap-8 sm:p-8">
      <div className="max-w-sm text-center sm:text-left">
        <p className="text-xs font-semibold uppercase tracking-wider text-[#8a9a6f]">
          focus-flow
        </p>
        <h2 className="mt-1.5 text-2xl font-semibold tracking-tight text-[#3c352a] sm:text-3xl">
          Grow while you focus.
        </h2>
        <p className="mt-2 text-sm leading-relaxed text-[#6b6255]">
          Every session you complete adds a little growth to your garden. Put
          the notebook down, start the timer, and let it add up.
        </p>
      </div>
      <img
        src={heroPlant}
        alt="A leafy potted plant beside a notebook and pen"
        className="h-48 w-auto shrink-0 sm:h-56"
      />
    </section>
  )
}
